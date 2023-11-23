package by.sapra.newsservice.storages;

import by.sapra.newsservice.config.AbstractDataTest;
import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.storages.models.NewsListModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = NewsStorageConf.class)
class NewsStorageTest extends AbstractDataTest {
    @Autowired
    private NewsStorage newsStorage;

    @Test
    void shouldReturnEmptyModelIfDatabaseIsEmpty() throws Exception {

        NewsFilter filter = new NewsFilter();
        filter.setPageSize(3);
        filter.setPageNumber(0);

        NewsListModel actual = newsStorage.findAll(filter);

        assertAll(() ->{
            assertNotNull(actual, "actual must not be null");
            assertNotNull(actual.getNews(), "news must not be null");
            assertEquals(0, actual.getCount(), "count must be 0");
            assertTrue(actual.getNews().isEmpty(), "new list must be empty");
        });
    }


}