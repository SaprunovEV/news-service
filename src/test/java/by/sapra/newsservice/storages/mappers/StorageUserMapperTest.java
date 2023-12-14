package by.sapra.newsservice.storages.mappers;

import by.sapra.newsservice.models.UserEntity;
import by.sapra.newsservice.storages.models.StorageUserItem;
import by.sapra.newsservice.storages.models.StorageUserList;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static by.sapra.newsservice.testUtils.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperConf.class)
class StorageUserMapperTest {
    @Autowired
    StorageUserMapper mapper;

    @MockBean
    StorageCategory2NewsMapper category2NewsMapper;

    @Test
    void shouldMapEntityToStorageUserItem() throws Exception {
        UserEntity expected = aUser().build();
        expected.setId(1L);

        StorageUserItem actual = mapper.entityToStorageUserItem(expected);

        assertStorageUserItem(expected, actual);
    }

    @Test
    void shouldMapEntityListToStorageUserItemList() throws Exception {
        List<UserEntity> expected = List.of(
                aUser().build(),
                aUser().build(),
                aUser().build()
        );

        expected.forEach(entity -> {
            entity.setId(1L);
            entity.setName(RandomString.make(10));
        });

        List<StorageUserItem> actual = mapper.entityListToStorageUserItemList(expected);

        assertStorageUserItemList(expected, actual);
    }

    @Test
    void shouldMapEntityListToStorageUserList() throws Exception {
        List<UserEntity> expected = List.of(
                aUser().build(),
                aUser().build(),
                aUser().build()
        );

        expected.forEach(entity -> {
            entity.setId(1L);
            entity.setName(RandomString.make(10));
        });

        StorageUserList actual = mapper.entityListToStorageUserList(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertStorageUserItemList(expected, actual.getUsers());
        });
    }

    private static void assertStorageUserItemList(List<UserEntity> expected, List<StorageUserItem> actual) {
        assertAll(() -> {
            assertNotNull(actual);
            for (int i = 0; i < actual.size(); i++) {
                assertStorageUserItem(expected.get(i), actual.get(i));
            }
        });
    }

    private static void assertStorageUserItem(UserEntity expected, StorageUserItem actual) {
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
        });
    }
}