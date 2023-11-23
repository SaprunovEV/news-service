package by.sapra.newsservice.services.impl;

import by.sapra.newsservice.services.mappers.NewsModelMapper;
import by.sapra.newsservice.services.models.Comment;
import by.sapra.newsservice.services.models.News;
import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.storages.NewsStorage;
import by.sapra.newsservice.storages.models.CommentModel;
import by.sapra.newsservice.storages.models.NewsListModel;
import by.sapra.newsservice.storages.models.NewsModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatabaseNewsServiceTest {
    @Mock
    private NewsStorage newsStorage;
    @Mock
    private NewsModelMapper newsModelMapper;

    @InjectMocks
    private DatabaseNewsService service;


    @Test
    void shouldNotReturnNull() throws Exception {

        when(newsStorage.findAll(any())).thenReturn(createModel(2));

        List<News> actual = service.findAll(new NewsFilter());

        assertNotNull(actual);
    }

    @Test
    void shouldCallNewsStorage() throws Exception {
        NewsFilter filter = new NewsFilter();

        NewsListModel newsModel = createModel(3);
        when(newsStorage.findAll(filter)).thenReturn(newsModel);

        service.findAll(filter);

        verify(newsStorage, times(1)).findAll(filter);
    }

    @Test
    void shouldReturnCorrectSize() throws Exception {
        NewsFilter filter = new NewsFilter();
        filter.setPageNumber(0);
        filter.setPageSize(3);

        NewsListModel model = createModel(filter.getPageSize());
        when(newsStorage.findAll(filter)).thenReturn(model);

        when(newsModelMapper.modelListToNewsList(model)).thenReturn(createListNews(filter.getPageSize()));

        List<News> actual = service.findAll(filter);

        assertEquals(filter.getPageSize(), actual.size());
    }

    @Test
    void shouldCallMapper() throws Exception {
        NewsFilter filter = new NewsFilter();
        filter.setPageNumber(0);
        filter.setPageSize(3);

        NewsListModel model = createModel(filter.getPageSize());
        when(newsStorage.findAll(filter)).thenReturn(model);

        List<News> actual = service.findAll(filter);

        verify(newsModelMapper, times(1)).modelListToNewsList(model);
    }

    private List<News> createListNews(long column) {
        ArrayList<News> news = new ArrayList<>();
        for (int i = 1; i <= column; i++) {
            news.add(createNews(i));
        }
        return news;
    }

    private News createNews(long i) {
        return News.builder()
                .id(i)
                .newsAbstract("abstract " + i)
                .body("body " + i)
                .comments(createComments(i))
                .title("title " + i)
                .build();
    }

    private List<Comment> createComments(long count) {
        ArrayList<Comment> comments = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            comments.add(Comment.builder().build());
        }

        return comments;
    }

    private NewsListModel createModel(int count) {
        ArrayList<NewsModel> news = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            news.add(NewsModel.builder()
                            .id(i)
                            .newsAbstract("abstract " + i)
                            .body("body " + i)
                            .comments(createCommentsModel(i))
                            .title("title " + i)
                        .build());
        }
        return NewsListModel.builder()
                .news(news)
                .count(count)
                .build();
    }

    private List<CommentModel> createCommentsModel(int count) {
        ArrayList<CommentModel> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(CommentModel.builder().build());
        }
        return list;
    }
}