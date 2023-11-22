package by.sapra.newsservice.models;

import by.sapra.newsservice.config.AbstractMigrationTest;
import by.sapra.newsservice.testUtils.UserTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest extends AbstractMigrationTest {
    @Test
    void shouldGiveId() throws Exception {
        UserEntity savedEntity = getTestDbFacade().save(UserTestDataBuilder.aUser());

        assertAll(() -> {
            assertNotNull(savedEntity);
            assertNotNull(savedEntity.getId());

            assertEquals(savedEntity.getName(), getTestDbFacade().find(savedEntity.getId(), UserEntity.class).getName());
        });
    }
}