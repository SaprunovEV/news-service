package by.sapra.newsservice.web.v1.mappers;

import by.sapra.newsservice.services.models.News;
import by.sapra.newsservice.web.v1.models.NewsItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = NewsMapperConf.class)
class NewsMapperTest {
    @Autowired
    NewsMapper newsMapper;

    @Test
    void shouldMapNewsToNewsItem() throws Exception {
        News expected = News.builder()
                .id(1L)
                .newsAbstract("test abstract")
                .comments(new ArrayList<>())
                .title("test title")
                .commentsCount(20L)
                .body("test body")
                .build();

        NewsItem actual = newsMapper.newsToNesItemList(expected);

        assertNewsItem(expected, actual);
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