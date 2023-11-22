package by.sapra.newsservice.models;

import by.sapra.newsservice.config.AbstractMigrationTest;
import by.sapra.newsservice.testUtils.NewsTestDataBuilder;
import by.sapra.newsservice.testUtils.UserTestDataBuilder;
import org.junit.jupiter.api.Test;

import static by.sapra.newsservice.testUtils.NewsTestDataBuilder.aNews;
import static by.sapra.newsservice.testUtils.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;

class NewsEntityTest extends AbstractMigrationTest {
    @Test
    void shouldSaveEntity() throws Exception {
        NewsEntity entity = getTestDbFacade()
                .save(
                        aNews()
                                .withUser(getTestDbFacade().persistedOnce(aUser())));

        assertAll(() -> {
            assertNotNull(entity);
            assertNotNull(entity.getId());
            assertNotNull(entity.getUser());
            assertNotNull(entity.getCreateAt());
            assertNotNull(entity.getUpdateAt());
        });
    }
}