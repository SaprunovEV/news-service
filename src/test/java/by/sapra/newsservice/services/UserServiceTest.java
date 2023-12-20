package by.sapra.newsservice.services;

import by.sapra.newsservice.models.errors.UserError;
import by.sapra.newsservice.services.mappers.UserServiceMapper;
import by.sapra.newsservice.services.models.ApplicationModel;
import by.sapra.newsservice.services.models.UserItemModel;
import by.sapra.newsservice.services.models.UserListModel;
import by.sapra.newsservice.services.models.filters.UserFilter;
import by.sapra.newsservice.storages.UserStorage;
import by.sapra.newsservice.storages.models.StorageUserItem;
import by.sapra.newsservice.storages.models.StorageUserList;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void whenFindById_theNotReturnNull() throws Exception {
        ApplicationModel<UserItemModel, UserError> actual = service.findUserById(1);

        assertNotNull(actual);
    }

    @Test
    void whenFindById_thenReturnApplicationModelWithoutError() throws Exception {
        long id = 1;

        StorageUserItem storageUser = createStorageUserItem(id);

        when(storage.findById(id)).thenReturn(Optional.of(storageUser));

        UserItemModel userModel = createUserItemModel(id);
        when(mapper.storageUserItemToUserItemModel(storageUser)).thenReturn(userModel);

        ApplicationModel<UserItemModel, UserError> actual = service.findUserById(id);


        assertAll(() -> {
            assertNotNull(actual);
            assertFalse(actual.hasError());
            assertNotNull(actual.getData());
            assertEquals(id, actual.getData().getId());
        });

        verify(storage, times(1)).findById(id);
        verify(mapper, times(1)).storageUserItemToUserItemModel(storageUser);
    }

    @Test
    void whenFindId_thenReturnError_ifUserNotFound() throws Exception {
        long id = 1;

        StorageUserItem storageUser = createStorageUserItem(id);

        when(storage.findById(id)).thenReturn(Optional.empty());

        ApplicationModel<UserItemModel, UserError> actual = service.findUserById(id);

        assertAll(() -> {
            assertNotNull(actual);
            assertTrue(actual.hasError());
            assertNotNull(actual.getError());
            String errorMessage = MessageFormat.format("Пользователь с ID {0} не найден!", id);
            assertEquals(errorMessage, actual.getError().getMessage());
        });

        verify(storage, times(1)).findById(id);
        verify(mapper, times(0)).storageUserItemToUserItemModel(storageUser);
    }

    @ParameterizedTest
    @MethodSource("getUserNames")
    void whenCreateUser_thenReturnSavedUser(String name) throws Exception {
        String username = name;
        long id = 1L;
        UserItemModel expected = UserItemModel.builder().name(username).build();

        StorageUserItem mapperResponse = StorageUserItem.builder().name(username).build();
        when(mapper.userItemModelToStorageUserItem(expected)).thenReturn(mapperResponse);

        Optional<StorageUserItem> optional = Optional.of(
                StorageUserItem
                        .builder()
                        .id(id)
                        .name(username)
                        .build()
        );
        when(storage.createNewUser(mapperResponse)).thenReturn(optional);

        UserItemModel result = UserItemModel.builder().name(username).id(id).build();
        when(mapper.storageUserItemToUserItemModel(optional.get())).thenReturn(result);

        ApplicationModel<UserItemModel, UserError> actual = service.createUser(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertFalse(actual.hasError());
            assertNotNull(actual.getData());
            assertNotNull(actual.getData().getId());
            assertEquals(expected.getName(), actual.getData().getName());
        });

        verify(mapper, times(1)).userItemModelToStorageUserItem(expected);
        verify(storage, times(1)).createNewUser(mapperResponse);
        verify(mapper, times(1)).storageUserItemToUserItemModel(optional.get());
    }

    @ParameterizedTest
    @MethodSource("getUserIds")
    void whenUpdateUser_thenReturnUpdatedUser(Long id) throws Exception {
        String username = "username " + id;
        UserItemModel expected = UserItemModel.builder().name(username).build();

        StorageUserItem mapperResponse = StorageUserItem.builder().name(username).build();
        when(mapper.userItemModelToStorageUserItem(expected)).thenReturn(mapperResponse);

        Optional<StorageUserItem> optional = Optional.of(
                StorageUserItem
                        .builder()
                        .id(id)
                        .name(username)
                        .build()
        );
        when(storage.updateUser(mapperResponse)).thenReturn(optional);

        UserItemModel result = UserItemModel.builder().name(username).id(id).build();
        when(mapper.storageUserItemToUserItemModel(optional.get())).thenReturn(result);

        ApplicationModel<UserItemModel, UserError> actual = service.updateUser(expected);

        assertAll(() -> {
            assertNotNull(actual, "не должен возвращать null");
            assertFalse(actual.hasError(), "не должен возвращать ошибку");
            assertNotNull(actual.getData(), "должен возвращать данные");
            assertEquals(actual.getData().getId(), id,"должен иметь правильный id");
            assertEquals(expected.getName(), actual.getData().getName(), "имена должны совпадать");
        });

        verify(mapper, times(1)).userItemModelToStorageUserItem(expected);
        verify(storage, times(1)).updateUser(mapperResponse);
        verify(mapper, times(1)).storageUserItemToUserItemModel(optional.get());
    }

    public static Stream<Arguments> getUserIds() {
        return Stream.of(
                Arguments.arguments(1L),
                Arguments.arguments(2L)
        );
    }

    @Test
    void whenCreateNewUser_withBusyName_thenReturnError() throws Exception {
        String username = "username";

        UserItemModel expected = UserItemModel.builder().name(username).build();

        StorageUserItem mapperResponse = StorageUserItem.builder().name(username).build();
        when(mapper.userItemModelToStorageUserItem(expected)).thenReturn(mapperResponse);

        when(storage.createNewUser(mapperResponse)).thenReturn(Optional.empty());

        ApplicationModel<UserItemModel, UserError> actual = service.createUser(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertTrue(actual.hasError());
            assertNotNull(actual.getError());
            String errorMessage = MessageFormat.format("Пользователь с именем {0} уже существует!", username);
            assertEquals(errorMessage, actual.getError().getMessage());
        });
    }

    public static Stream<Arguments> getUserNames() {
        return Stream.of(
                Arguments.arguments(RandomString.make(10)),
                Arguments.arguments(RandomString.make(10))
        );
    }

    private StorageUserList createStorageUserList(int count) {
        ArrayList<StorageUserItem> users = new ArrayList<>();

        for (long i = 1; i < count; i++) {
            users.add(
                    createStorageUserItem(i)
            );
        }

        return StorageUserList.builder()
                .users(users)
                .build();
    }

    private UserItemModel createUserItemModel(long id) {
        return UserItemModel.builder()
                .name("user name " + id)
                .id(id)
                .build();
    }

    private StorageUserItem createStorageUserItem(long id) {
        return StorageUserItem.builder()
                .name("user name " + id)
                .id(id)
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