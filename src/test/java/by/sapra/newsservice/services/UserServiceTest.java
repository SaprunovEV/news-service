package by.sapra.newsservice.services;

import by.sapra.newsservice.services.mappers.UserServiceMapper;
import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.services.models.filters.UserFilter;
import by.sapra.newsservice.storages.UserStorage;
import by.sapra.newsservice.storages.models.StorageUserItem;
import by.sapra.newsservice.storages.models.StorageUserList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserServiceConf.class)
class UserServiceTest {

    @Autowired
    UserService service;
    @MockBean
    private UserStorage storage;
    @MockBean
    private UserServiceMapper mapper;

    @Test
    void whenFindAll_thenReturnAllUsers() throws Exception {
        UserFilter filter = createFilter(0,3);

        StorageUserList storageUserList = createStorageUserList(filter.getPageSize());

        when(storage.findAll(filter)).thenReturn(storageUserList);

        UserListModel result = createUserListModel(filter.getPageSize());
        when(mapper.storageUserListToUserListModel(storageUserList)).thenReturn(result);

        UserListModel actual = service.findAllUsers(filter);

        assertAll(() -> {
            assertNotNull(actual);
        });

        verify(storage, times(1)).findAll(filter);
        verify(mapper, times(1)).storageUserListToUserListModel(storageUserList);
    }

    @Test
    void whenFindAll_thenReturnEmptyModel_ifUsersNotExist() throws Exception {
        UserFilter filter = createFilter(0,3);

        StorageUserList storageUserList = createStorageUserList(0);

        when(storage.findAll(filter)).thenReturn(storageUserList);

        UserListModel result = createUserListModel(0);
        when(mapper.storageUserListToUserListModel(storageUserList)).thenReturn(result);

        UserListModel actual = service.findAllUsers(filter);

        assertAll(() -> {
            assertNotNull(actual);
        });

        verify(storage, times(1)).findAll(filter);
        verify(mapper, times(1)).storageUserListToUserListModel(storageUserList);
    }

    private static StorageUserList createStorageUserList(int count) {
        ArrayList<StorageUserItem> users = new ArrayList<>();

        for (long i = 1; i < count; i++) {
            users.add(
                    StorageUserItem.builder()
                            .id(i)
                            .name("user name " + i)
                            .build()
            );
        }

        return StorageUserList.builder()
                .users(users)
                .build();
    }

    private static UserListModel createUserListModel(int column) {
        ArrayList<UserItemModel> users = new ArrayList<>();

        for (long i = 1; i <= column; i++) {
            users.add(
                    UserItemModel.builder()
                            .name("user name " + i)
                            .id(i)
                            .build()
            );
        }

        return UserListModel.builder()
                .users(users)
                .build();
    }

    private UserFilter createFilter(int pageNumber, int pageSize) {
        return UserFilter.builder()
                .pageSize(pageSize)
                .pageNumber(pageNumber)
                .build();
    }
}