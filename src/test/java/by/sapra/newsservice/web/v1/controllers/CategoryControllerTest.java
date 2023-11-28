package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.models.errors.CategoryNotFound;
import by.sapra.newsservice.services.CategoryService;
import by.sapra.newsservice.services.models.ApplicationModel;
import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.services.models.News;
import by.sapra.newsservice.testUtils.StringTestUtils;
import by.sapra.newsservice.web.v1.AbstractErrorControllerTest;
import by.sapra.newsservice.web.v1.mappers.CategoryMapper;
import by.sapra.newsservice.web.v1.mappers.ErrorMapper;
import by.sapra.newsservice.web.v1.models.*;
import net.javacrumbs.jsonunit.JsonAssert;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest extends AbstractErrorControllerTest {

    @MockBean
    private CategoryService service;
    @MockBean
    private CategoryMapper mapper;
    @MockBean
    private ErrorMapper errorMapper;

    @Test
    void whenFindAll_thenReturnOk() throws Exception {
        String pageNumber = "0";
        String pageSize = "3";
        mockMvc.perform(getWithPagination(pageNumber, pageSize))
                .andExpect(status().isOk());
    }

    @Test
    void whenFindAll_thenReturnAllCategory() throws Exception {
        String pageNumber = "0";
        String pageSize = "3";

        CategoryFilter filter = createFilter(pageNumber, pageSize);

        List<Category> list = createCategoryList(Long.parseLong(pageSize));

        when(service.findAll(eq(filter))).thenReturn(list);

        when(mapper.categoryItemListToCategoryListResponse(list))
                .thenReturn(createCategoryListResponse(Long.parseLong(pageSize)));

        String actual = mockMvc.perform(getWithPagination(pageNumber, pageSize))
                .andReturn().getResponse().getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/categories/find_all_with_pagination_response.json");

        JsonAssert.assertJsonEquals(expected, actual);

        verify(service, times(1)).findAll(filter);
        verify(mapper, times(1)).categoryItemListToCategoryListResponse(list);
    }

    @Test
    void whenFindAll_withAnother_thenReturnAllCategory() throws Exception {
        String pageNumber = "0";
        String pageSize = "4";

        CategoryFilter filter = createFilter(pageNumber, pageSize);

        List<Category> list = createCategoryList(Long.parseLong(pageSize));

        when(service.findAll(eq(filter))).thenReturn(list);

        when(mapper.categoryItemListToCategoryListResponse(list))
                .thenReturn(createCategoryListResponse(Long.parseLong(pageSize)));

        String actual = mockMvc.perform(getWithPagination(pageNumber, pageSize))
                .andReturn().getResponse().getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/categories/find_all_with_another_data_response.json");

        JsonAssert.assertJsonEquals(expected, actual);

        verify(service, times(1)).findAll(filter);
        verify(mapper, times(1)).categoryItemListToCategoryListResponse(list);
    }

    @Test
    void shouldReturnOkFromCategoryById() throws Exception {
        CategoryWithNews category = createCategoryWithNews(1, 3);
        ApplicationModel model = mock(ApplicationModel.class);
        when(service.findById(1)).thenReturn(model);
        when(model.hasError()).thenReturn(false);
        when(model.getData()).thenReturn(category);

        mockMvc.perform(get(getUrl() + "/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void whenFindById_thenReturnCorrectResponse() throws Exception {
        long id = 1;

        CategoryWithNews category = createCategoryWithNews(id, 3);
        ApplicationModel model = mock(ApplicationModel.class);
        when(service.findById(id)).thenReturn(model);
        when(model.hasError()).thenReturn(false);
        when(model.getData()).thenReturn(category);

        CategoryResponse categoryResponse = createCategoryResponseWithNews(id, 3);
        when(mapper.categoryToCategoryResponse(category)).thenReturn(categoryResponse);

        String actual = mockMvc.perform(get(getUrl() + "/{id}", id))
                .andReturn().getResponse().getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/categories/find_by_id_"+id+"_response.json");

        JsonAssert.assertJsonEquals(expected, actual);

        verify(service, times(1)).findById(id);
        verify(mapper, times(1)).categoryToCategoryResponse(category);
    }

    @Test
    void whenFindById_withAnotherData_thenReturnCorrectResponse() throws Exception {
        long id = 2;

        CategoryWithNews category = createCategoryWithNews(id, 3);
        ApplicationModel<CategoryWithNews, CategoryNotFound> model = mock(ApplicationModel.class);
        when(service.findById(id)).thenReturn(model);
        when(model.hasError()).thenReturn(false);
        when(model.getData()).thenReturn(category);

        CategoryResponse categoryResponse = createCategoryResponseWithNews(id, 3);
        when(mapper.categoryToCategoryResponse(category)).thenReturn(categoryResponse);

        String actual = mockMvc.perform(get(getUrl() + "/{id}", id))
                .andReturn().getResponse().getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/categories/find_by_id_"+id+"_response.json");

        JsonAssert.assertJsonEquals(expected, actual);

        verify(service, times(1)).findById(id);
        verify(mapper, times(1)).categoryToCategoryResponse(category);
    }

    @Test
    void whenCategoryNotFound_thenReturnError() throws Exception {
        long id = 2;

        CategoryWithNews category = createCategoryWithNews(id, 3);
        ApplicationModel<CategoryWithNews, CategoryNotFound> model = mock(ApplicationModel.class);
        when(service.findById(id)).thenReturn(model);
        when(model.hasError()).thenReturn(true);
        CategoryNotFound categoryNotFoundError = createCategoryNotFoundError(id);
        when(model.getError()).thenReturn(categoryNotFoundError);

        when(errorMapper.errorToCategoryErrorResponse(categoryNotFoundError)).thenReturn(createCategoryErrorResponse(id));

        MockHttpServletResponse response = mockMvc.perform(get(getUrl() + "/{id}", id))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actual = response.getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/categories/category_not_found_error_response.json");


        JsonAssert.assertJsonEquals(expected, actual);

        verify(service, times(1)).findById(id);
        verify(errorMapper, times(1)).errorToCategoryErrorResponse(model.getError());
    }

    private ErrorResponse createCategoryErrorResponse(long id) {
        return CategoryErrorResponse.builder()
                .message(MessageFormat.format("Категория с {0} не найдена!", id))
                .build();
    }

    private CategoryNotFound createCategoryNotFoundError(long id) {
        return CategoryNotFound.builder()
                .message(MessageFormat.format("Категория с {0} не найдена!", id))
                .build();
    }

    private CategoryResponse createCategoryResponseWithNews(long id, int count) {
        ArrayList<NewsItem> list = new ArrayList<>();

        for (long i = 0; i < count; i++) {
            list.add(createNewsItem(i + 1));
        }

        return CategoryResponse.builder()
                .id(id)
                .name("Test category " + id)
                .news(list)
                .build();
    }

    private CategoryWithNews createCategoryWithNews(long id, int countOfNews) {
        List<News> news = createNewsList(countOfNews);

        for (long i = 0; i < countOfNews; i++) {
            news.add(createOneNews(i + 1));
        }

        return CategoryWithNews.builder()
                .id(id)
                .name("Test category " + id)
                .news(news)
                .build();
    }

    private List<News> createNewsList(int countOfNews) {
        ArrayList<News> news = new ArrayList<>();

        for (long i = 0; i < countOfNews; i++) {
            News news1 = createOneNews(i + 1);
            news.add(news1);
        }

        return news;
    }
    private NewsItem createNewsItem(long id) {
        return NewsItem.builder()
                .id(id)
                .title("Test title " + id)
                .newsAbstract("test abstract " + id)
                .body("test body " + id)
                .commentsCount(id)
                .build();
    }


    private News createOneNews(long id) {
        return News.builder()
                .id(id)
                .title("Test title " + id)
                .newsAbstract("test abstract " + id)
                .body("test body " + id)
                .build();
    }

    private static CategoryListResponse createCategoryListResponse(long count) {
        ArrayList<CategoryItem> list = new ArrayList<>();
        for (long i = 1; i <= count; i++) {
            list.add(createCategoryItem(i, "Test category " + i));
        }
        return CategoryListResponse.builder()
                .categories(list)
                .build();
    }

    private static CategoryItem createCategoryItem(long id, String name) {
        return CategoryItem.builder().id(id).name(name).newsCount(id).build();
    }

    @NotNull
    private static List<Category> createCategoryList(long count) {
        ArrayList<Category> list = new ArrayList<>();
        for (long i = 1; i <= count; i++) {
            list.add(createCategory(i, "Test category " + i));
        }
        return list;
    }

    private static Category createCategory(long id, String name) {
        return Category.builder().id(id).name(name).newsCount(id).build();
    }

    private CategoryFilter createFilter(String pageNumber, String pageSize) {
        CategoryFilter filter = new CategoryFilter();
        filter.setPageNumber(Integer.valueOf(pageNumber));
        filter.setPageSize(Integer.valueOf(pageSize));

        return filter;
    }

    @NotNull
    private MockHttpServletRequestBuilder getWithPagination(String pageNumber, String pageSize) {
        return get(getUrl()).param("pageNumber", pageNumber).param("pageSize", pageSize);
    }

    @Override
    public String getUrl() {
        return "/api/v1/categories";
    }
}