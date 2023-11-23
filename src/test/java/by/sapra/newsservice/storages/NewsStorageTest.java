package by.sapra.newsservice.storages;

import by.sapra.newsservice.config.AbstractDataTest;
import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.models.UserEntity;
import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.storages.mappers.StorageNewsMapper;
import by.sapra.newsservice.storages.models.NewsListModel;
import by.sapra.newsservice.storages.models.NewsModel;
import by.sapra.newsservice.testUtils.TestDataBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.sapra.newsservice.testUtils.NewsTestDataBuilder.aNews;
import static by.sapra.newsservice.testUtils.UserTestDataBuilder.aUser;
import static java.util.function.Function.identity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = NewsStorageConf.class)
class NewsStorageTest extends AbstractDataTest {
    @Autowired
    private NewsStorage newsStorage;

    @Autowired
    private StorageNewsMapper mapper;

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
    void shouldReadPageNews() throws Exception {
        int pageNumber = 0;
        int pageSize = 3;

        NewsFilter filter = getNewsFilter(pageNumber, pageSize);

        int count = 5;
        Map<Long, NewsEntity> map = saveNewsEntities(count);

        List<NewsEntity> list = map.values().stream().toList().subList(0, 3);


        when(mapper.entitiesListToNewsListModel(eq(list), any()))
                .thenReturn(NewsListModel.builder()
                        .count(list.size())
                        .news(list.stream().map(news -> NewsModel.builder().build()).toList())
                        .build());

        NewsListModel actual = newsStorage.findAll(filter);

        assertAll(() -> {
            assertEquals(actual.getNews().size(), actual.getCount(), "size of news must be equals count");
            assertEquals(pageSize, actual.getCount(), "count must be equals page size");
        });
    }

    @Test
    void shouldReturnCorrectModel() throws Exception {
        int pageNumber = 0;
        int pageSize = 3;

        NewsFilter filter = getNewsFilter(pageNumber, pageSize);

        int count = 5;
        Map<Long, NewsEntity> expected = saveNewsEntities(count);

        NewsListModel actual = newsStorage.findAll(filter);

        assertListNews(expected, actual, pageSize);
    }

    private void assertListNews(Map<Long, NewsEntity> expected, NewsListModel actual, int count) {
        assertAll(() -> {
            assertEquals(count, actual.getCount(), "not correct size of page.");
            for (NewsModel model: actual.getNews()) {
                assertTrue(expected.containsKey(model.getId()), MessageFormat.format("entity with id {0} not found.", model.getId()));
            }
        });
    }

    private Map<Long, NewsEntity> saveNewsEntities(int count) {
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
        return list.stream().collect(Collectors.toMap(NewsEntity::getId, identity()));
    }

    @NotNull
    private static NewsFilter getNewsFilter(int pageNumber, int pageSize) {
        NewsFilter filter = new NewsFilter();
        filter.setPageSize(pageSize);
        filter.setPageNumber(pageNumber);
        return filter;
    }
}