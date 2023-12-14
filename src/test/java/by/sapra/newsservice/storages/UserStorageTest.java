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