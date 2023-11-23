package by.sapra.newsservice.storages;

import by.sapra.newsservice.config.AbstractDataTest;
import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.models.UserEntity;
import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.storages.models.NewsListModel;
import by.sapra.newsservice.testUtils.TestDataBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static by.sapra.newsservice.testUtils.NewsTestDataBuilder.aNews;
import static by.sapra.newsservice.testUtils.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = NewsStorageConf.class)
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
    void shouldReadPageNews() throws Exception {
        int pageNumber = 0;
        int pageSize = 3;

        NewsFilter filter = getNewsFilter(pageNumber, pageSize);

        int count = 5;
        saveNewsEntities(count);



        NewsListModel actual = newsStorage.findAll(filter);

        assertAll(() -> {
            assertEquals(actual.getNews().size(), actual.getCount(), "size of news must be equals count");
            assertEquals(pageSize, actual.getCount(), "count must be equals page size");
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