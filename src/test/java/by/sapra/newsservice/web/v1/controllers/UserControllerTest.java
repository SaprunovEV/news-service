package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.models.errors.UserError;
import by.sapra.newsservice.services.UserService;
import by.sapra.newsservice.services.models.ApplicationModel;
import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.services.models.filters.UserFilter;
import by.sapra.newsservice.testUtils.StringTestUtils;
import by.sapra.newsservice.web.v1.AbstractErrorControllerTest;
import by.sapra.newsservice.web.v1.mappers.UserResponseMapper;
import by.sapra.newsservice.web.v1.models.UpsertUserRequest;
import by.sapra.newsservice.web.v1.models.UserItemResponse;
import by.sapra.newsservice.web.v1.models.UserListResponse;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {UserController.class})
public class UserControllerTest extends AbstractErrorControllerTest {
    @MockBean
    private UserService service;
    @MockBean
    private UserResponseMapper mapper;

    public static Stream<Arguments> idsForSearch() {
        return Stream.of(
                Arguments.arguments(1),
                Arguments.arguments(2)
        );
    }

    public static Stream<Arguments> getUserId() {
        return Stream.of(
                Arguments.arguments(1),
                Arguments.arguments(2)
        );
    }

    @Test
    void whenCallFindAllUsers_thenReturnStatusOk_thenReturnUsers() throws Exception {
        int pageNumber = 0;
        int pageSize = 3;

        UserListModel usersListModel = createUserListModel(pageSize);

        UserFilter filter = createFilter(pageNumber, pageSize);

        when(service.findAllUsers(filter)).thenReturn(usersListModel);

        UserListResponse userListResponse = createUserListResponse(3);
        when(mapper.userListModelToUserListResponse(usersListModel)).thenReturn(userListResponse);

        String actual = mockMvc.perform(
                get(
                        getUrl())
                        .param("pageSize", String.valueOf(pageSize))
                        .param("pageNumber", String.valueOf(pageNumber))
                )
                    .andExpect(status().isOk())
                    .andReturn().getResponse()
                    .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/users/find_all_users_response.json");

        JsonAssert.assertJsonEquals(expected, actual);

        verify(service, times(1)).findAllUsers(filter);
        verify(mapper, times(1)).userListModelToUserListResponse(usersListModel);
    }
    @ParameterizedTest
    @MethodSource("idsForSearch")
    void whenFindById_thenReturnUserItemResponse(long id) throws Exception {
        UserItemModel serviceResponse = createUserItemModel(id);

        ApplicationModel model = mock(ApplicationModel.class);
        when(model.hasError()).thenReturn(false);
        when(model.getData()).thenReturn(serviceResponse);

        when(service.findUserById(id)).thenReturn(model);

        UserItemResponse mapperResponse = createUserItemResponse(id);
        when(mapper.serviceUserItemToUserItemResponse(serviceResponse)).thenReturn(mapperResponse);

        String actual = mockMvc.perform(get(getUrl() + "/{id}", id))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/users/find_by_id_" + id + "_response.json");

        JsonAssert.assertJsonEquals(expected, actual);

        verify(service, times(1)).findUserById(id);
        verify(mapper, times(1)).serviceUserItemToUserItemResponse(serviceResponse);
    }

    @Test
    void whenFindById_thenReturnUserItemError() throws Exception {
        long id  = 1;
        UserItemModel serviceResponse = createUserItemModel(id);

        ApplicationModel model = mock(ApplicationModel.class);
        when(model.hasError()).thenReturn(true);
        when(model.getError()).thenReturn(UserError.builder().message("Пользователь с 1 не найден!").build());

        when(service.findUserById(id)).thenReturn(model);

        MockHttpServletResponse response = mockMvc.perform(get(getUrl() + "/{id}", id))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actual = response.getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/users/user_not_found_error.json");

        JsonAssert.assertJsonEquals(expected, actual);

        verify(service, times(1)).findUserById(id);
    }

    @ParameterizedTest
    @MethodSource("getUserId")
    void whenCreateNewUser_thenReturnNewUserResponse(long id) throws Exception {
        String username = "username " + id;
        UpsertUserRequest request = UpsertUserRequest.builder()
                .name(username)
                .build();

        UserItemModel mapperResponse = UserItemModel.builder().name(username).build();
        when(mapper.requestToUserItemModel(request)).thenReturn(mapperResponse);

        UserItemModel serviceResponse = UserItemModel.builder().name(username).id(id).build();
        ApplicationModel<UserItemModel, UserError> model = mock(ApplicationModel.class);

        when(model.hasError()).thenReturn(false);
        when(model.getData()).thenReturn(serviceResponse);

        when(service.createUser(mapperResponse)).thenReturn(model);

        UserItemResponse result = UserItemResponse.builder().name(username).id(id).build();
        when(mapper.serviceUserItemToUserItemResponse(serviceResponse)).thenReturn(result);

        String actual = mockMvc.perform(
                        post(getUrl())
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/users/create_" + id + "_user_response.json");

        JsonAssert.assertJsonEquals(expected, actual);

        verify(mapper, times(1)).requestToUserItemModel(request);
        verify(service, times(1)).createUser(mapperResponse);
        verify(model, times(1)).hasError();
        verify(model, times(1)).getData();
        verify(mapper, times(1)).serviceUserItemToUserItemResponse(serviceResponse);
    }

    @Test
    void whenCreateUser_and_UsernameAlresdyExist_thenReturnError() throws Exception {
        long id = 1;
        String username = "username " + id;
        UpsertUserRequest request = UpsertUserRequest.builder()
                .name(username)
                .build();

        UserItemModel mapperResponse = UserItemModel.builder().name(username).build();
        when(mapper.requestToUserItemModel(request)).thenReturn(mapperResponse);

        ApplicationModel<UserItemModel, UserError> model = mock(ApplicationModel.class);

        when(model.hasError()).thenReturn(true);
        when(model.getError()).thenReturn(UserError.builder()
                        .message(MessageFormat.format("Пользователь с именем {0} уже существует!", username))
                .build());

        when(service.createUser(mapperResponse)).thenReturn(model);

        MockHttpServletResponse response = mockMvc.perform(
                        post(getUrl())
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actual = response.getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/users/username_already_exist_response.json");

        JsonAssert.assertJsonEquals(expected, actual);

        verify(mapper, times(1)).requestToUserItemModel(request);
        verify(service, times(1)).createUser(mapperResponse);
        verify(model, times(1)).hasError();
        verify(model, times(1)).getError();
    }

    private static UserFilter createFilter(int pageNumber, int pageSize) {
        return UserFilter.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
    }

    private UserListModel createUserListModel(int count) {
        return UserListModel.builder()
                .users(createUserItemModels(count))
                .build();
    }

    private UserListResponse createUserListResponse(int count) {
        return UserListResponse.builder()
                .users(createUserItemResponses(count))
                .build();
    }

    private List<UserItemResponse> createUserItemResponses(int count) {
        ArrayList<UserItemResponse> users = new ArrayList<>();

        for (long i = 1; i < count + 1; i++) {
            users.add(
                    createUserItemResponse(i)
            );
        }

        return users;
    }

    private UserItemResponse createUserItemResponse(long id) {
        return UserItemResponse.builder().id(id).name("user name " + id).build();
    }


    private List<UserItemModel> createUserItemModels(int count) {
        ArrayList<UserItemModel> users = new ArrayList<>();
        for (long i = 1; i < count + 1; i++) {
            users.add(
                    createUserItemModel(i)
            );
        }
        return users;
    }

    private UserItemModel createUserItemModel(long i) {
        return UserItemModel.builder()
                .id(i)
                .name("user name " + i)
                .build();
    }

    @Override
    public String getUrl() {
        return "/api/v1/users";
    }
}
