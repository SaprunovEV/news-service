package by.sapra.newsservice.services.mappers;

import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.storages.models.StorageUserItem;
import by.sapra.newsservice.storages.models.StorageUserList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperConf.class)
class UserServiceMapperTest {
    @Autowired
    UserServiceMapper mapper;

    @Test
    void shouldMapStorageUserListToUserListModel() throws Exception {
        StorageUserList expected = StorageUserList.builder()
                .users(List.of(
                        StorageUserItem.builder().name("name").id(1L).build()
                ))
                .build();

        UserListModel actual = mapper.storageUserListToUserListModel(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getUsers());
            for (int i = 0; i < actual.getUsers().size(); i++) {
                assertServiceUserItem(expected.getUsers().get(i), actual.getUsers().get(i));
            }
        });
    }

    @Test
    void shouldMapStorageUseerItemToUserItemModel() throws Exception {
        StorageUserItem expected = StorageUserItem.builder().name("name").id(1L).build();

        UserItemModel actual = mapper.storageUserItemToUserItemModel(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getId(), actual.getId());
        });
    }

    private void assertServiceUserItem(StorageUserItem expected, UserItemModel actual) {
        assertAll(() -> {
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
        });
    }

    @Test
    void shouldMapStorageUserItemToEntity() throws Exception {
        String username = "username";
        UserItemModel expected = UserItemModel.builder()
                .name(username)
                .id(1L)
                .build();

        StorageUserItem actual = mapper.userItemModelToStorageUserItem(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
        });
    }

    @Test
    void shouldMapStorageUserItemToEntityWithIdNull() throws Exception {
        String username = "username";
        UserItemModel expected = UserItemModel.builder()
                .name(username)
                .id(null)
                .build();

        StorageUserItem actual = mapper.userItemModelToStorageUserItem(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertNull(actual.getId());
            assertEquals(expected.getName(), actual.getName());
        });
    }
}