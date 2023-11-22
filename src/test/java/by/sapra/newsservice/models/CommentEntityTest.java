package by.sapra.newsservice.models;

import by.sapra.newsservice.config.AbstractMigrationTest;
import by.sapra.newsservice.testUtils.CommentTestDataBuilder;
import by.sapra.newsservice.testUtils.NewsTestDataBuilder;
import by.sapra.newsservice.testUtils.TestDataBuilder;
import by.sapra.newsservice.testUtils.UserTestDataBuilder;
import org.junit.jupiter.api.Test;

import static by.sapra.newsservice.testUtils.CommentTestDataBuilder.aComment;
import static by.sapra.newsservice.testUtils.NewsTestDataBuilder.aNews;
import static by.sapra.newsservice.testUtils.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;

class CommentEntityTest extends AbstractMigrationTest {
    @Test
    void shouldSaveEntity() throws Exception {
        TestDataBuilder<UserEntity> user = getTestDbFacade().persistedOnce(aUser());
        CommentEntity actual = getTestDbFacade().save(
                aComment()
                        .withNews(getTestDbFacade().persistedOnce(aNews().withUser(user)))
                        .withUser(user)
        );

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getId());
            assertNull(actual.getParent());

            assertNotNull(actual.getNews());
            assertNotNull(actual.getUser());

            assertNotNull(actual.getCreateAt());
            assertNotNull(actual.getUpdateAt());
        });
    }
}