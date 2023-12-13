package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.services.UserService;
import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.services.models.filters.UserFilter;
import by.sapra.newsservice.testUtils.StringTestUtils;
import by.sapra.newsservice.web.v1.AbstractErrorControllerTest;
import by.sapra.newsservice.web.v1.mappers.UserResponseMapper;
import by.sapra.newsservice.web.v1.models.UserItemResponse;
import by.sapra.newsservice.web.v1.models.UserListResponse;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {UserController.class})
public class UserControllerTest extends AbstractErrorControllerTest {
    @MockBean
    private UserService service;
    @MockBean
    private UserResponseMapper mapper;

    @Test
    void whenCallFindAllUsers_thenReturnStatusOk_thenReturnUsers() throws Exception {
        int pageNumber = 0;
        int pageSize = 3;

        UserListModel usersListModel = createUserListModel(pageSize);

        UserFilter filter = createFilter(pageNumber, pageSize);

        when(service.findAllUsers(filter)).thenReturn(usersListModel);

        UserListResponse userListResponse = createUserListResponse(3);
        when(mapper.userListModelToUserListResponse(usersListModel)).thenReturn(userListResponse);

        String actual = mockMvc.perform(get(getUrl()))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/users/find_all_users_response.json");

        JsonAssert.assertJsonEquals(expected, actual);

        verify(service, times(1)).findAllUsers(filter);
        verify(mapper, times(1)).userListModelToUserListResponse(usersListModel);
    }

    private static UserFilter createFilter(int pageNumber, int pageSize) {
        return UserFilter.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
    }

    private UserListModel createUserListModel(int count) {
        return UserListModel.builder()
                .users(createUserItemModel(count))
                .build();
    }

    private UserListResponse createUserListResponse(int count) {
        return UserListResponse.builder()
                .users(createUserItemResponses(count))
                .build();
    }

    private List<UserItemResponse> createUserItemResponses(int count) {
        ArrayList<UserItemResponse> users = new ArrayList<>();

        for (long i = 0; i < count; i++) {
            users.add(
                    UserItemResponse.builder().id(i).name("user name " + i).build()
            );
        }

        return users;
    }


    private List<UserItemModel> createUserItemModel(int count) {
        ArrayList<UserItemModel> users = new ArrayList<>();
        for (long i = 0; i < count; i++) {
            users.add(
                    UserItemModel.builder()
                            .id(i)
                            .name("user name " + i)
                            .build()
            );
        }
        return users;
    }

    @Override
    public String getUrl() {
        return "/api/v1/users";
    }
}
