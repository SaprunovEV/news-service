package by.sapra.newsservice.storages;

import by.sapra.newsservice.config.AbstractDataTest;
import by.sapra.newsservice.models.UserEntity;
import by.sapra.newsservice.services.models.filters.UserFilter;
import by.sapra.newsservice.storages.mappers.MapperConf;
import by.sapra.newsservice.storages.models.StorageUserItem;
import by.sapra.newsservice.storages.models.StorageUserList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static by.sapra.newsservice.testUtils.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;

@ContextHierarchy({
        @ContextConfiguration(classes = MapperConf.class),
        @ContextConfiguration(classes = UserStorageConf.class)
})
class UserStorageTest extends AbstractDataTest {
    @Autowired
    UserStorage storage;


    @Test
    void whenFindAll_shouldReturnPageOfUsers() throws Exception {
        int pageNumber = 0;
        int pageSize = 3;
        UserFilter filter = createFilter(pageNumber, pageSize);

        List<UserEntity> expected = Stream.of(
                getTestDbFacade().save(aUser().withName("name 1")),
                getTestDbFacade().save(aUser().withName("name 2")),
                getTestDbFacade().save(aUser().withName("name 3")),
                getTestDbFacade().save(aUser().withName("name 4")),
                getTestDbFacade().save(aUser().withName("name 5"))
        ).limit(3).toList();

        StorageUserList actual = storage.findAll(filter);

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getUsers());
            assertUsers(expected, actual.getUsers());
        });
    }

    @Test
    void whenFindAll_thenReturnEmptyModelIfUsersNotFound() throws Exception {
        int pageNumber = 0;
        int pageSize = 3;
        UserFilter filter = createFilter(pageNumber, pageSize);

        StorageUserList actual = storage.findAll(filter);

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getUsers());
            assertTrue(actual.getUsers().isEmpty());
        });
    }

    @Test
    void whenFindById_thenReturnUserById() throws Exception {
        UserEntity entity = getTestDbFacade().save(aUser().withName("username"));

        StorageUserItem expected = StorageUserItem.builder().id(entity.getId()).name(entity.getName()).build();

        Optional<StorageUserItem> actual = storage.findById(entity.getId());

        assertAll(() -> {
            assertNotNull(actual);
            assertTrue(actual.isPresent());
            assertEquals(expected, actual.get());
        });
    }

    @Test
    void whenFindById_and_userNotFound_thenReturnEmptyOptional() throws Exception {
        Optional<StorageUserItem> actual = storage.findById(1);

        assertAll(() -> {
            assertNotNull(actual);
            assertTrue(actual.isEmpty());
        });
    }

    @Test
    void whenCreateNewUser_thenSaveNewUser_andReturnNotEmptyOptional() throws Exception {
        String username = "username";
        StorageUserItem expected = StorageUserItem.builder().name(username).build();

        Optional<StorageUserItem> actual = storage.createNewUser(expected);

        assertAll(() -> {
            assertTrue(actual.isPresent());
            assertEquals(expected.getName(),actual.get().getName());
            assertNotNull(getTestDbFacade().find(actual.get().getId(), UserEntity.class));
        });
    }

    @Test
    void whenCreateNewUser_andNameAlreadyExist_thenReturnEmptyOptional() throws Exception {
        String username = "username";

        getTestDbFacade().save(aUser().withName(username));

        StorageUserItem expected = StorageUserItem.builder().name(username).build();
        Optional<StorageUserItem> actual = storage.createNewUser(expected);

        assertAll(() -> {
            assertTrue(actual.isEmpty());
        });
    }

    private void assertUsers(List<UserEntity> expected, List<StorageUserItem> users) {
        assertAll(() -> {
            List<String> names = users.stream().map(StorageUserItem::getName).toList();

            expected.forEach(ex -> assertTrue(names.contains(ex.getName())));
        });
    }

    private UserFilter createFilter(int pageNumber,int pageSize) {
        return UserFilter.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
    }
}