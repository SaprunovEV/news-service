package by.sapra.newsservice.web.v1.mappers;

import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.web.v1.models.UpsertUserRequest;
import by.sapra.newsservice.web.v1.models.UserItemResponse;
import by.sapra.newsservice.web.v1.models.UserListResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperConf.class)
class UserResponseMapperTest {
    @Autowired
    UserResponseMapper mapper;

    @Test
    void shouldMapUserListModelToUserListResponse() throws Exception {
        UserListModel expected = UserListModel.builder()
                .users(List.of(
                        UserItemModel.builder().id(1L).name("name").build()
                ))
                .build();

        UserListResponse actual = mapper.userListModelToUserListResponse(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getUsers());
            for (int i = 0; i < actual.getUsers().size(); i++) {
                assertUserItemResponse(expected.getUsers().get(i), actual.getUsers().get(i));
            }
        });
    }

    @Test
    void shouldMapUserItemModelToUserItemResponse() throws Exception {
        UserItemModel expected = UserItemModel.builder().id(1L).name("name").build();

        UserItemResponse actual = mapper.serviceUserItemToUserItemResponse(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getId(), actual.getId());
        });
    }

    @Test
    void shouldMapRequestToUserItemModel() throws Exception {
        UpsertUserRequest expected = UpsertUserRequest.builder()
                .name("username")
                .build();

        UserItemModel actual = mapper.requestToUserItemModel(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getName(), actual.getName());
        });
    }

    private void assertUserItemResponse(UserItemModel expected, UserItemResponse actual) {
        assertAll(() -> {
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
        });
    }
}