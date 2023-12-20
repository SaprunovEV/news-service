package by.sapra.newsservice.services.mappers;

import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.services.models.News;
import by.sapra.newsservice.storages.models.CommentListModel;
import by.sapra.newsservice.storages.models.NewsListModel;
import by.sapra.newsservice.storages.models.NewsModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperConf.class)
class NewsModelMapperTest {
    @Autowired
    NewsModelMapper mapper;

    @Test
    void shouldMapNewsModelToNews() throws Exception {
        NewsModel expected = createNews(1L, 20L,"title", "abstract", "body");

        News actual = mapper.newsModelToNews(expected);

        assertNews(expected, actual);
    }

    @Test
    void shouldMapNewsListModelToNewsList() throws Exception {
        int count = 4;
        NewsListModel expected = createNewsListModel(count);

        List<News> actual = mapper.modelListToNewsList(expected);

        assertNewsLiat(expected, actual);
    }

    private void assertNewsLiat(NewsListModel expected, List<News> actual) {
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getNews().size(), actual.size());
            for (int i = 0; i < actual.size(); i++) {
                assertNews(expected.getNews().get(i), actual.get(i));
            }
        });
    }

    private NewsListModel createNewsListModel(int count) {
        ArrayList<NewsModel> news = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            news.add(
                    createNews(i, i, "abstract " + i, "title " + i, "body " + i)
            );
        }

        return NewsListModel.builder()
                .count(21)
                .news(news)
                .build();
    }

    private void assertNews(NewsModel expected, News actual) {
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getCommentSize(), actual.getCommentsCount());
            assertEquals(expected.getNewsAbstract(), actual.getNewsAbstract());
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getBody(), actual.getBody());
            assertEquals(expected.getTitle(), expected.getTitle());
            expected.getCategoryIds().forEach(id -> assertTrue(actual.getCategoryIds().contains(id)));
        });
    }

    private static NewsModel createNews(long i, long commentCount, String newsAbstract, String title, String body) {
        return NewsModel.builder()
                .id(i)
                .newsAbstract(newsAbstract)
                .comments(new CommentListModel(new NewsEntity(), entity -> null, 10))
                .title(title)
                .commentSize(commentCount)
                .body(body)
                .categoryIds(List.of(
                        1L, 2L
                ))
                .build();
    }
}