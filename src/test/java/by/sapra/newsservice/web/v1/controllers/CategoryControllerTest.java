package by.sapra.newsservice.web.v1.controllers;

import by.sapra.newsservice.models.errors.CategoryError;
import by.sapra.newsservice.services.CategoryService;
import by.sapra.newsservice.services.models.*;
import by.sapra.newsservice.testUtils.StringTestUtils;
import by.sapra.newsservice.web.v1.AbstractErrorControllerTest;
import by.sapra.newsservice.web.v1.mappers.CategoryMapper;
import by.sapra.newsservice.web.v1.models.*;
import net.bytebuddy.utility.RandomString;
import net.javacrumbs.jsonunit.JsonAssert;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest extends AbstractErrorControllerTest {

    @MockBean
    private CategoryService service;
    @MockBean
    private CategoryMapper mapper;

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
        ApplicationModel<CategoryWithNews, CategoryError> model = mock(ApplicationModel.class);
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
        ApplicationModel<CategoryWithNews, CategoryError> model = mock(ApplicationModel.class);
        when(service.findById(id)).thenReturn(model);
        when(model.hasError()).thenReturn(true);
        CategoryError categoryErrorError = createCategoryNotFoundError(id);
        when(model.getError()).thenReturn(categoryErrorError);

        assertValidateId(get(getUrl() + "/{id}", id), status().isNotFound(), "/responses/v1/errors/category_not_found_error_response.json");

        verify(service, times(1)).findById(id);
    }

    @Test
    void whenIdIsNegative_shouldReturnError() throws Exception {
        long id = -1;

        assertValidateId(get(getUrl() + "/{id}", id), status().isBadRequest(), "/responses/v1/errors/negative_id_error_response.json");
    }

    @Test
    void whenIdIsZero_shouldReturnError() throws Exception {
        long id = 0;

        assertValidateId(get(getUrl() + "/{id}", id), status().isBadRequest(), "/responses/v1/errors/negative_id_error_response.json");
    }

    @Test
    void whenCreateTheCategory_ThenReturnSavedCategory() throws Exception {
        String name = "Test category 1";
        UpsertCategoryRequest request = createUpsertCategoryRequest(name);

        CategoryWithNews category = createCategoryWithNews(0L, 0);
        when(mapper.requestToCategoryWithNews(request)).thenReturn(category);
        CategoryWithNews savedCategory = createCategoryWithNews(1L, 0);
        ApplicationModel<CategoryWithNews, CategoryError> model = mock(ApplicationModel.class);
        when(service.saveCategory(category)).thenReturn(model);
        when(model.hasError()).thenReturn(false);
        when(model.getData()).thenReturn(savedCategory);
        when(mapper.categoryToCategoryResponse(savedCategory))
                .thenReturn(createCategoryResponseWithNews(1L, 0));

        String actual = mockMvc.perform(
                        post(getUrl())
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/categories/save_new_category_request.json");

        JsonAssert.assertJsonEquals(expected, actual);

        verify(mapper, times(1)).requestToCategoryWithNews(request);
        verify(service, times(1)).saveCategory(category);
        verify(model, times(1)).hasError();
        verify(model, times(1)).getData();
        verify(mapper, times(1)).categoryToCategoryResponse(savedCategory);
    }

    @Test
    void whenSavedExistingCategory_thenReturnError() throws Exception {
        String name = "Test category 1";

        UpsertCategoryRequest request = createUpsertCategoryRequest(name);

        CategoryWithNews category = createCategoryWithNews(0L, 0);
        when(mapper.requestToCategoryWithNews(request)).thenReturn(category);
        ApplicationModel<CategoryWithNews, CategoryError> model = mock(ApplicationModel.class);
        when(service.saveCategory(category)).thenReturn(model);
        when(model.hasError()).thenReturn(true);
        CategoryError categoryExist = createCategoryCategoryExist(name);
        when(model.getError()).thenReturn(categoryExist);
        when(model.getError()).thenReturn(categoryExist);

        assertValidateId(post(getUrl())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)), status().isBadRequest(), "/responses/v1/errors/save_existing_category_response.json");

        verify(mapper, times(1)).requestToCategoryWithNews(request);
        verify(model, times(1)).hasError();
        verify(model, times(1)).getError();
        verify(service, times(1)).saveCategory(category);
    }

    @Test
    void whenCategoryName_isEmpty_thenReturnError() throws Exception {
        UpsertCategoryRequest request = createUpsertCategoryRequest(null);
        MockHttpServletRequestBuilder method = post(getUrl())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        assertValidation(method, "/responses/v1/errors/create_empty_category_error_response.json");
    }
    @ParameterizedTest
    @MethodSource("invalidSizeCategoryName")
    void whenCategoryName_isLessThen5_OrMoreThen50_thenReturnError(String name) throws Exception {
        UpsertCategoryRequest request = createUpsertCategoryRequest(name);
        MockHttpServletRequestBuilder method = post(getUrl())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        assertValidation(method, "/responses/v1/errors/create_not_less_or_more_then_posible_category_name_error_response.json");
    }

    @Test
    void whenUpdateCategoryWithId1_thenReturnUpdatedCategory() throws Exception {
        long id = 1;

        String name2update = "Test category " + id;

        UpsertCategoryRequest request = createUpsertCategoryRequest(name2update);

        CategoryWithNews category2update = CategoryWithNews.builder()
                .name(name2update)
                .id(id)
                .news(new ArrayList<>())
                .build();

        when(mapper.requestWithIdToCategoryWithNews(request, id)).thenReturn(category2update);

        CategoryWithNews updatedCategory = createCategoryWithNews(id, 3);
        ApplicationModel<CategoryWithNews, CategoryError> model = mock(ApplicationModel.class);
        when(service.updateCategory(category2update)).thenReturn(model);

        when(model.hasError()).thenReturn(false);
        when(model.getData()).thenReturn(updatedCategory);

        when(mapper.categoryToCategoryResponse(updatedCategory)).thenReturn(createCategoryResponseWithNews(id, 3));

        String actual = mockMvc.perform(
                        put(getUrl() + "/{id}", id)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(UpsertCategoryRequest.builder().name(name2update).build()))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/v1/categories/update_category_with_id_1_request.json");

        JsonAssert.assertJsonEquals(expected, actual);

        verify(mapper, times(1)).requestWithIdToCategoryWithNews(request, id);
        verify(service, times(1)).updateCategory(category2update);
        verify(mapper, times(1)).categoryToCategoryResponse(updatedCategory);
    }

    @Test
    void whenUpdateIsNotPossible_thenReturnError() throws Exception {
        long id = 2l;
        String name2update = "Test category " + id;

        UpsertCategoryRequest request = createUpsertCategoryRequest(name2update);

        CategoryWithNews category2update = CategoryWithNews.builder()
                .name(name2update)
                .id(id)
                .news(new ArrayList<>())
                .build();

        when(mapper.requestWithIdToCategoryWithNews(request, id)).thenReturn(category2update);

        ApplicationModel<CategoryWithNews, CategoryError> model = mock(ApplicationModel.class);
        when(service.updateCategory(category2update)).thenReturn(model);

        when(model.hasError()).thenReturn(true);
        CategoryError error = createCategoryNotFoundError(id);
        when(model.getError()).thenReturn(error);

        assertValidateId(put(getUrl() + "/{id}", id)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)), status().isBadRequest(), "/responses/v1/errors/category_not_found_error_response.json");

        verify(mapper, times(1)).requestWithIdToCategoryWithNews(request, id);
        verify(service, times(1)).updateCategory(category2update);
        verify(model, times(1)).hasError();
        verify(model, times(1)).getError();
    }

    @Test
    void whenDeleteCategory_thenReturnNoContent() throws Exception {
        long id = 1L;

        mockMvc.perform(delete(getUrl() + "/{id}", id))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteCategory(id);
    }

    @Test
    void whenDeletedIdIsNegative_shouldReturnError() throws Exception {
        long id = -1;

        assertValidateId(delete(getUrl() + "/{id}", id), status().isBadRequest(), "/responses/v1/errors/negative_id_error_response.json");
    }

    @Test
    void whenDeletedIdIsZero_shouldReturnError() throws Exception {
        long id = 0;

        assertValidateId(delete(getUrl() + "/{id}", id), status().isBadRequest(), "/responses/v1/errors/negative_id_error_response.json");
    }

    @Test
    void whenUpdatedCategoryName_isEmpty_thenReturnError() throws Exception {
        UpsertCategoryRequest request = createUpsertCategoryRequest(null);
        MockHttpServletRequestBuilder method = put(getUrl() + "/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        assertValidation(method,"/responses/v1/errors/create_empty_category_error_response.json");
    }

    @ParameterizedTest
    @MethodSource("invalidSizeCategoryName")
    void whenUpdatedCategoryName_isLessThen5_OrMoreThen50_thenReturnError(String name) throws Exception {
        UpsertCategoryRequest request = createUpsertCategoryRequest(name);
        MockHttpServletRequestBuilder method = put(getUrl() + "/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        assertValidation(method, "/responses/v1/errors/create_not_less_or_more_then_posible_category_name_error_response.json");
    }

    @Test
    void whenUpdatedIdIsNegative_shouldReturnError() throws Exception {
        long id = -1;

        assertValidateId(put(getUrl() + "/{id}", id), status().isBadRequest(), "/responses/v1/errors/negative_id_error_response.json");
    }

    @Test
    void whenUpdatedIdIsZero_shouldReturnError() throws Exception {
        long id = 0;

        assertValidateId(put(getUrl() + "/{id}", id), status().isBadRequest(), "/responses/v1/errors/negative_id_error_response.json");
    }

    private void assertValidateId(MockHttpServletRequestBuilder id, ResultMatcher BadRequest, String path) throws Exception {
        MockHttpServletResponse response = mockMvc.perform(id)
                .andExpect(BadRequest)
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actual = response.getContentAsString();

        String expected = StringTestUtils.readStringFromResources(path);

        JsonAssert.assertJsonEquals(expected, actual);
    }
    private void assertValidation(MockHttpServletRequestBuilder method, String path) throws Exception {

        assertValidateId(method, status().isBadRequest(), path);
    }

    public static Stream<Arguments> invalidSizeCategoryName() {
        return Stream.of(
                Arguments.arguments(RandomString.make(4)),
                Arguments.arguments(RandomString.make(51))
        );
    }

    private CategoryError createCategoryCategoryExist(String name) {
        return CategoryError.builder()
                .message(MessageFormat.format("Категория с именем {0} уже существует!", name))
                .build();
    }

    private UpsertCategoryRequest createUpsertCategoryRequest(String name) {
        return UpsertCategoryRequest.builder().name(name).build();
    }

    private CategoryError createCategoryNotFoundError(long id) {
        return CategoryError.builder()
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
                .categoryIds(List.of(1L, 2L, 3L))
                .build();
    }


    private News createOneNews(long id) {
        return News.builder()
                .id(id)
                .title("Test title " + id)
                .newsAbstract("test abstract " + id)
                .body("test body " + id)
                .categoryIds(List.of(1L, 2L, 3L))
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