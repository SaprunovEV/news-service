package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.services.NewsService;
import by.sapra.newsservice.services.models.Comment;
import by.sapra.newsservice.services.models.News;
import by.sapra.newsservice.services.models.filters.NewsFilter;
import by.sapra.newsservice.testUtils.StringTestUtils;
import by.sapra.newsservice.web.v1.AbstractErrorControllerTest;
import by.sapra.newsservice.web.v1.mappers.NewsMapper;
import by.sapra.newsservice.web.v1.models.NewsItem;
import by.sapra.newsservice.web.v1.models.NewsListResponse;
import net.javacrumbs.jsonunit.JsonAssert;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {NewsController.class})
class NewsControllerTest extends AbstractErrorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NewsService newsService;
    @MockBean
    private NewsMapper newsMapper;

    @Test
    void whenFindAll_thenReturnOk() throws Exception {
        mockMvc.perform(getWithPagination("0", "3"))
                .andExpect(status().isOk());

    }

    @Test
    void whenFindAll_andUsesParam_thenReturnCorrectResponse() throws Exception {
        int pageNumber = 0;
        int pageSize = 3;

        NewsFilter newsFilter = createNewsFilter(pageNumber, pageSize);

        List<News> newsList = createNewsList(pageSize);
        when(newsService.findAll(newsFilter)).thenReturn(newsList);

        NewsListResponse response = getNewsListResponse(pageSize);
        when(newsMapper.newsListToNewsListResponse(newsList)).thenReturn(response);

        String actualContent = mockMvc.perform(getWithPagination(Integer.toString(pageNumber), Integer.toString(pageSize)))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();



        String expectedContent = StringTestUtils.readStringFromResources("/responses/v1/news/find_all_response_1.json");

        JsonAssert.assertJsonEquals(expectedContent, actualContent);

        verify(newsService, times(1)).findAll(newsFilter);
        verify(newsMapper, times(1)).newsListToNewsListResponse(newsList);
    }

    @NotNull
    private static NewsFilter createNewsFilter(int pageNumber, int pageSize) {
        NewsFilter newsFilter = new NewsFilter();
        newsFilter.setPageNumber(pageNumber);
        newsFilter.setPageSize(pageSize);
        return newsFilter;
    }

    @Test
    void whenFindAll_andUsesAnotherParam_thenReturnCorrectResponse() throws Exception {
        int pageNumber = 0;
        int pageSize = 4;

        NewsFilter newsFilter = createNewsFilter(pageNumber, pageSize);
        List<News> newsList = createNewsList(pageSize);

        when(newsService.findAll(newsFilter)).thenReturn(createNewsList(pageSize));

        NewsListResponse response = getNewsListResponse(pageSize);
        when(newsMapper.newsListToNewsListResponse(newsList)).thenReturn(response);

        String actualContent = mockMvc.perform(getWithPagination("0", "4"))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String expectedContent = StringTestUtils.readStringFromResources("/responses/v1/news/find_all_response_2.json");

        JsonAssert.assertJsonEquals(expectedContent, actualContent);

        verify(newsService, times(1)).findAll(newsFilter);
        verify(newsMapper, times(1)).newsListToNewsListResponse(newsList);
    }

    @NotNull
    private static List<News> createNewsList(int count) {
        ArrayList<News> news = new ArrayList<>();

        for (long i = 0; i < count; i++) {
            News news1 = createOneNews(i + 1);
            news.add(news1);
        }

        return news;
    }

    private static News createOneNews(long i) {
        return News.builder()
                .id(i)
                .title("Test title " + i)
                .newsAbstract("test abstract " + i)
                .body("test body " + i)
                .comments(List.of(
                        Comment.builder()
                                .count(i)
                                .build()
                ))
                .categoryIds(List.of(1L, 2L, 3L))
                .build();
    }

    private NewsListResponse getNewsListResponse(int count) {
        List<NewsItem> news = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            news.add(createNewsItem(i + 1));
        }

        return NewsListResponse.builder()
                .news(news)
                .build();
    }

    private NewsItem createNewsItem(long id) {
        return NewsItem.builder()
                .id(id)
                .title("Test title " + id)
                .newsAbstract("test abstract " + id)
                .body("test body " + id)
                .commentsCount(id)
                .categoryIds(List.of(1L, 2L, 3L))
                .build();
    }

    @NotNull
    private MockHttpServletRequestBuilder getWithPagination(String pageNumber, String pageSize) {
        return get(getUrl()).param("pageSize", pageSize).param("pageNumber", pageNumber);
    }

    @Override
    public String getUrl() {
        return "/api/v1/news";
    }
}