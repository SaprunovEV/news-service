package by.sapra.newsservice.models;

import by.sapra.newsservice.config.AbstractMigrationTest;
import by.sapra.newsservice.testUtils.*;
import org.junit.jupiter.api.Test;

import static by.sapra.newsservice.testUtils.Category2NewsTestDataBuilder.aCategory2News;
import static by.sapra.newsservice.testUtils.CategoryTestDataBuilder.aCategory;
import static by.sapra.newsservice.testUtils.NewsTestDataBuilder.aNews;
import static by.sapra.newsservice.testUtils.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;

class Category2NewsTest extends AbstractMigrationTest {
    @Test
    void shouldCreateTheEntity() throws Exception {
        TestDataBuilder<CategoryEntity> category = getTestDbFacade().persistedOnce(aCategory());
        TestDataBuilder<NewsEntity> news = getTestDbFacade().persistedOnce(aNews().withUser(getTestDbFacade().persistedOnce(aUser())));
        Category2News actual = getTestDbFacade().save(
                aCategory2News()
                        .withCategory(category)
                        .withNews(news)
        );

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getId());

            assertEquals(category.build().getName(), actual.getCategory().getName());
            assertEquals(news.build().getTitle(), actual.getNews().getTitle());
        });
    }

    @Test
    void shouldBeRemovedIfCategoryWillRemove() throws Exception {
        TestDataBuilder<CategoryEntity> category = getTestDbFacade().persistedOnce(aCategory());
        TestDataBuilder<NewsEntity> news = getTestDbFacade().persistedOnce(aNews().withUser(getTestDbFacade().persistedOnce(aUser())));
        Category2News savedLink = getTestDbFacade().save(
                aCategory2News()
                        .withCategory(category)
                        .withNews(news)
        );

        getTestDbFacade().delete(category.build());

        assertNull(getTestDbFacade().find(savedLink.getId(), Category2News.class));
    }

    @Test
    void shouldBeRemovedIfNewsWillRemove() throws Exception {
        TestDataBuilder<CategoryEntity> category = getTestDbFacade().persistedOnce(aCategory());
        TestDataBuilder<NewsEntity> news = getTestDbFacade().persistedOnce(aNews().withUser(getTestDbFacade().persistedOnce(aUser())));
        Category2News savedLink = getTestDbFacade().save(
                aCategory2News()
                        .withCategory(category)
                        .withNews(news)
        );

        getTestDbFacade().delete(news.build());

        assertNull(getTestDbFacade().find(savedLink.getId(), Category2News.class));
    }
}