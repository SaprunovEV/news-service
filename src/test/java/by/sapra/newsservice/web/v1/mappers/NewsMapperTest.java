package by.sapra.newsservice.web.v1.mappers;

import by.sapra.newsservice.services.models.News;
import by.sapra.newsservice.web.v1.models.NewsItem;
import by.sapra.newsservice.web.v1.models.NewsListResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = NewsMapperConf.class)
class NewsMapperTest {
    @Autowired
    NewsMapper newsMapper;

    @Test
    void shouldMapNewsToNewsItem() throws Exception {
        News expected = createNews(1L, 20L, "test abstract", "test title", "test body");

        NewsItem actual = newsMapper.newsToNesItemList(expected);

        assertNewsItem(expected, actual);
    }

    @Test
    void shouldDoSomething() throws Exception {
        List<News> expected = createListNews(4);

        NewsListResponse actual = newsMapper.newsListToNewsListResponse(expected);

        assertNewsListResponse(expected, actual);
    }

    private void assertNewsListResponse(List<News> expected, NewsListResponse actual) {
        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getNews());
            assertEquals(expected.size(), actual.getNews().size());
            for (int i = 0; i < actual.getNews().size(); i++) {
                assertNewsItem(expected.get(i), actual.getNews().get(i));
            }
        });
    }

    private List<News> createListNews(int count) {
        ArrayList<News> news = new ArrayList<>();
        for (long i = 0; i < count; i++) {
            news.add(
                    createNews(i, i, "test abstract " + i, "test title " + i, "test body " + i)
            );
        }
        return news;
    }

    private static News createNews(long i, long i1, String i2, String i3, String i4) {
        return News.builder()
                .id(i)
                .newsAbstract(i2)
                .comments(new ArrayList<>())
                .title(i3)
                .commentsCount(i1)
                .body(i4)
                .build();
    }

    private void assertNewsItem(News expected, NewsItem actual) {
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getTitle(), actual.getTitle());
            assertEquals(expected.getNewsAbstract(), actual.getNewsAbstract());
            assertEquals(expected.getCommentsCount(), actual.getCommentsCount());
            assertEquals(expected.getBody(), actual.getBody());
        });
    }
}