package by.sapra.newsservice.storages;

import by.sapra.newsservice.config.AbstractDataTest;
import by.sapra.newsservice.models.Category2News;
import by.sapra.newsservice.models.CategoryEntity;
import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.models.UserEntity;
import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.storages.mappers.MapperConf;
import by.sapra.newsservice.storages.models.NewsListModel;
import by.sapra.newsservice.storages.models.NewsModel;
import by.sapra.newsservice.testUtils.TestDataBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static by.sapra.newsservice.testUtils.Category2NewsTestDataBuilder.aCategory2News;
import static by.sapra.newsservice.testUtils.CategoryTestDataBuilder.aCategory;
import static by.sapra.newsservice.testUtils.NewsTestDataBuilder.aNews;
import static by.sapra.newsservice.testUtils.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;

@ContextHierarchy({
        @ContextConfiguration(classes = MapperConf.class),
        @ContextConfiguration(classes = NewsStorageConf.class)
})
class NewsStorageTest extends AbstractDataTest {
    @Autowired
    private NewsStorage newsStorage;


    @Test
    void shouldReturnEmptyModelIfDatabaseIsEmpty() throws Exception {

        int pageNumber = 0;
        int pageSize = 3;
        NewsFilter filter = getNewsFilter(pageNumber, pageSize);

        NewsListModel actual = newsStorage.findAll(filter);

        assertAll(() ->{
            assertNotNull(actual, "actual must not be null");
            assertNotNull(actual.getNews(), "news must not be null");
            assertEquals(0, actual.getCount(), "count must be 0");
            assertTrue(actual.getNews().isEmpty(), "new list must be empty");
        });
    }

    @Test
    void shouldReturnCorrectModel() throws Exception {
        int pageNumber = 0;
        int pageSize = 3;

        NewsFilter filter = getNewsFilter(pageNumber, pageSize);

        int count = 5;
        List<NewsEntity> savedEntities = saveNewsEntities(count).stream().sorted(Comparator.comparing(NewsEntity::getId)).toList();

        List<NewsEntity> expected = savedEntities.subList(0, 3);

        NewsListModel actual = newsStorage.findAll(filter);

        assertAll(() -> {
            assertNotNull(actual, "actual must not be null");
            assertNotNull(actual.getNews(), "news must not be null");
            assertEquals(expected.size(), actual.getCount(), "count must be 3");
            for (int i = 0; i < 3; i++) {
                assertModel(expected.get(i), actual.getNews().get(i));
            }
        });

    }

        @Test
    void shouldFilterByCategoryId() throws Exception {
        int pageNumber = 0;
        int pageSize = 3;

        NewsFilter filter = getNewsFilter(pageNumber, pageSize);


        TestDataBuilder<CategoryEntity> targetCategoryBuilder = getTestDbFacade().persistedOnce(aCategory().withName("targetName"));
        TestDataBuilder<CategoryEntity> categoryBuilder = getTestDbFacade().persistedOnce(aCategory().withName("notTargetName"));

        filter.setCategory(targetCategoryBuilder.build().getId());

        TestDataBuilder<UserEntity> userBuilder = getTestDbFacade().persistedOnce(aUser());

        List<Category2News> links = List.of(
                saveCategoryLinks(targetCategoryBuilder, userBuilder, "title 1"),
                saveCategoryLinks(categoryBuilder, userBuilder, "title 2"),
                saveCategoryLinks(targetCategoryBuilder, userBuilder, "title 3"),
                saveCategoryLinks(categoryBuilder, userBuilder, "title 4"),
                saveCategoryLinks(targetCategoryBuilder, userBuilder, "title 5")
        );

        List<NewsEntity> expected = links.stream()
                .filter(link -> link.getCategory().getId().equals(targetCategoryBuilder.build().getId())).map(Category2News::getNews).toList();

        NewsListModel actual = newsStorage.findAll(filter);

        assertAll(() -> {
            assertNotNull(actual, "не должен возвращать null");
            assertNotNull(actual.getNews(), "список новостей тоже не null");
            assertEquals(pageSize, actual.getCount(), "количество новостей равно pageSize");
            assertEquals(expected.size(), actual.getNews().size(), "ожидаемое количество равно пришедшему");
            for (int i = 0; i < expected.size(); i++) {
                assertModelWithCategory(expected.get(i), actual.getNews().get(i), filter.getCategory());
            }
        });
    }

    @Test
    void shouldFilterByAnotherCategoryId() throws Exception {
        int pageNumber = 0;
        int pageSize = 3;

        NewsFilter filter = getNewsFilter(pageNumber, pageSize);


        TestDataBuilder<CategoryEntity> targetCategoryBuilder = getTestDbFacade().persistedOnce(aCategory().withName("targetName"));
        TestDataBuilder<CategoryEntity> categoryBuilder = getTestDbFacade().persistedOnce(aCategory().withName("notTargetName"));

        filter.setCategory(categoryBuilder.build().getId());

        TestDataBuilder<UserEntity> userBuilder = getTestDbFacade().persistedOnce(aUser());

        List<Category2News> links = List.of(
                saveCategoryLinks(targetCategoryBuilder, userBuilder, "title 1"),
                saveCategoryLinks(categoryBuilder, userBuilder, "title 2"),
                saveCategoryLinks(targetCategoryBuilder, userBuilder, "title 3"),
                saveCategoryLinks(categoryBuilder, userBuilder, "title 4"),
                saveCategoryLinks(targetCategoryBuilder, userBuilder, "title 5")
        );

        List<NewsEntity> expected = links.stream()
                .filter(link -> link.getCategory().getId().equals(categoryBuilder.build().getId())).map(Category2News::getNews).toList();

        NewsListModel actual = newsStorage.findAll(filter);

        assertAll(() -> {
            assertNotNull(actual, "не должен возвращать null");
            assertNotNull(actual.getNews(), "список новостей тоже не null");
            assertEquals(2, actual.getCount(), "количество новостей равно pageSize");
            assertEquals(expected.size(), actual.getNews().size(), "ожидаемое количество равно пришедшему");
            for (int i = 0; i < expected.size(); i++) {
                assertModelWithCategory(expected.get(i), actual.getNews().get(i), filter.getCategory());
            }
        });
    }

    @Test
    void shouldReturnEmptyListIfCategoryNotPresent() throws Exception {
        int pageNumber = 0;
        int pageSize = 3;

        NewsFilter filter = getNewsFilter(pageNumber, pageSize);


        TestDataBuilder<CategoryEntity> targetCategoryBuilder = getTestDbFacade().persistedOnce(aCategory().withName("targetName"));
        TestDataBuilder<CategoryEntity> categoryBuilder = getTestDbFacade().persistedOnce(aCategory().withName("notTargetName"));

        filter.setCategory(110000L);

        TestDataBuilder<UserEntity> userBuilder = getTestDbFacade().persistedOnce(aUser());


        saveCategoryLinks(targetCategoryBuilder, userBuilder, "title 1");
        saveCategoryLinks(categoryBuilder, userBuilder, "title 2");
        saveCategoryLinks(targetCategoryBuilder, userBuilder, "title 3");
        saveCategoryLinks(categoryBuilder, userBuilder, "title 4");
        saveCategoryLinks(targetCategoryBuilder, userBuilder, "title 5");


        NewsListModel actual = newsStorage.findAll(filter);

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getNews());
            assertTrue(actual.getNews().isEmpty());
            assertEquals(0, actual.getCount());
        });
    }

    @Test
    void shouldFilterByOwnerId() throws Exception {
        int pageNumber = 0;
        int pageSize = 3;

        NewsFilter filter = getNewsFilter(pageNumber, pageSize);

        TestDataBuilder<UserEntity> targetUser = getTestDbFacade().persistedOnce(aUser().withName("targetUser"));
        TestDataBuilder<UserEntity> notTargetUser = getTestDbFacade().persistedOnce(aUser().withName("notTargetUser"));

        filter.setOwner(targetUser.build().getId());

        TestDataBuilder<CategoryEntity> categoryBuilder = getTestDbFacade().persistedOnce(aCategory());

        List<Category2News> links = List.of(
                saveCategoryLinks(categoryBuilder, targetUser, "title 1"),
                saveCategoryLinks(categoryBuilder, notTargetUser, "title 1"),
                saveCategoryLinks(categoryBuilder, targetUser, "title 1"),
                saveCategoryLinks(categoryBuilder, notTargetUser, "title 1"),
                saveCategoryLinks(categoryBuilder, targetUser, "title 1")
        );

        List<NewsEntity> expected = links.stream()
                .map(Category2News::getNews).filter(news -> news.getUser().getId().equals(targetUser.build().getId())).toList();

        NewsListModel actual = newsStorage.findAll(filter);

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getNews());
            assertEquals(pageSize, actual.getCount());
            assertEquals(expected.size(), actual.getNews().size());
            for (int i = 0; i < actual.getNews().size(); i++) {
                assertModelWithUser(expected.get(i), actual.getNews().get(i));
            }
        });
    }

    private void assertModelWithUser(NewsEntity expected, NewsModel actual) {
        assertAll(() -> {
            assertModel(expected, actual);
            assertEquals(expected.getUser().getId(), actual.getOwner());
        });
    }

    private void assertModelWithCategory(NewsEntity entity, NewsModel newsModel, Long category) {
        assertAll(() -> {
            assertModel(entity, newsModel);
            assertTrue(newsModel.getCategoryIds().contains(category), "категория должна быть отфильтрована");
        });
    }

    private Category2News saveCategoryLinks(TestDataBuilder<CategoryEntity> targetCategoryBuilder, TestDataBuilder<UserEntity> userBuilder, String title) {
        return getTestDbFacade().save(
                aCategory2News()
                        .withCategory(targetCategoryBuilder)
                        .withNews(getTestDbFacade().persistedOnce(
                                aNews()
                                        .withUser(userBuilder)
                                        .withTitle(title)
                        ))
        );
    }

    private void assertModel(NewsEntity expected, NewsModel actual) {
        assertAll(() -> {
            assertEquals(expected.getId(), actual.getId(), "id должны совпадать");
            assertEquals(expected.getUser().getId(), actual.getOwner(), "ID владельца должны совпадать");
            assertEquals(expected.getTitle(), actual.getTitle(), "заголовки должны совпадать");
        });
    }

    private List<NewsEntity> saveNewsEntities(int count) {
        TestDataBuilder<UserEntity> user = getTestDbFacade().persistedOnce(aUser());
        ArrayList<NewsEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(
                    getTestDbFacade().save(
                            aNews()
                                    .withUser(user)
                                    .withTitle("test title " + i)
                    )
            );
        }
        return list;
    }

    @NotNull
    private static NewsFilter getNewsFilter(int pageNumber, int pageSize) {
        NewsFilter filter = new NewsFilter();
        filter.setPageSize(pageSize);
        filter.setPageNumber(pageNumber);
        return filter;
    }
}