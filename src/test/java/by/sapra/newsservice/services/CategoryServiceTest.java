package by.sapra.newsservice.services;

import by.sapra.newsservice.models.errors.CategoryError;
import by.sapra.newsservice.services.mappers.CategoryModelMapper;
import by.sapra.newsservice.services.models.*;
import by.sapra.newsservice.storages.CategoryStorage;
import by.sapra.newsservice.storages.models.CategoryListModel;
import by.sapra.newsservice.storages.models.CategoryModel;
import by.sapra.newsservice.storages.models.FullCategoryModel;
import by.sapra.newsservice.storages.models.NewsModel;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CategoryServiceConf.class)
class CategoryServiceTest  {
    @Autowired
    CategoryService service;
    @Autowired
    private CategoryStorage storage;
    @Autowired
    private CategoryModelMapper mapper;

    @AfterEach
    void tearDown() {
        Mockito.reset(storage, mapper);
    }

    @Test
    void shouldNotReturnNull() throws Exception {
        CategoryFilter filter = createFilter(3, 0);

        List<Category> actual = service.findAll(filter);

        assertNotNull(actual);
    }

    @Test
    void shouldReturnEmptyCategoryListIfStorageWillReturnEmptyModel() throws Exception {
        CategoryFilter filter = createFilter(3, 0);

        CategoryListModel categoryListModel = createCategoryListModel(0);

        when(storage.findAll(filter)).thenReturn(categoryListModel);

        List<Category> expected = createCategoryList(0);
        when(mapper.categoryListModelToCategoryList(categoryListModel)).thenReturn(expected);

        List<Category> actual = service.findAll(filter);

        assertIterableEquals(expected, actual);

        verify(storage, times(1)).findAll(filter);
        verify(mapper, times(1)).categoryListModelToCategoryList(categoryListModel);
    }

    @Test
    void shouldCallStorageAndMapperAndReturnCategoryList() throws Exception {
        int pageSize = 3;
        CategoryFilter filter = createFilter(pageSize, 1);

        CategoryListModel categoryListModel = createCategoryListModel(pageSize);

        when(storage.findAll(filter)).thenReturn(categoryListModel);

        List<Category> expected = createCategoryList(pageSize);
        when(mapper.categoryListModelToCategoryList(categoryListModel)).thenReturn(expected);

        List<Category> actual = service.findAll(filter);

        assertIterableEquals(expected, actual);

        verify(storage, times(1)).findAll(filter);
        verify(mapper, times(1)).categoryListModelToCategoryList(categoryListModel);
    }

    @Test
    void shouldReturnModelWithDataIfCategoryWillPresentToStorage() throws Exception {
        long id = 1;

        Optional<FullCategoryModel> expected = Optional.of(FullCategoryModel.builder()
                        .news(new ArrayList<>())
                        .id(id)
                        .name("test name")
                .build());
        when(storage.findById(id)).thenReturn(expected);
        when(mapper.fullCategoryToCategoryWithNews(expected.get())).thenReturn(CategoryWithNews.builder()
                .news(new ArrayList<>())
                .id(id)
                .name("test name")
                .build());

        ApplicationModel<CategoryWithNews, CategoryError> actual = service.findById(id);

        assertAll(() -> {
            assertFalse(actual.hasError());
            assertNotNull(actual.getData());
            assertNotNull(actual.getData().getNews());
            assertEquals(expected.get().getId(), actual.getData().getId());
            assertEquals(expected.get().getName(), actual.getData().getName());
        });
    }

    @Test
    void shouldReturnError_whenStorageHaveNotCategory() throws Exception {
        long id = 1L;
        when(storage.findById(id)).thenReturn(Optional.empty());

        ApplicationModel<CategoryWithNews, CategoryError> actual = service.findById(id);

        assertAll(() -> {
            assertNotNull(actual);
            assertTrue(actual.hasError());
            assertNotNull(actual.getError());
        });

        verify(storage, times(1)).findById(id);
    }


    @Test
    void shouldNotReturnNull_whenCallSavedMethod() throws Exception {
        CategoryWithNews expected = CategoryWithNews.builder().build();

        ApplicationModel<CategoryWithNews, CategoryError> actual = service.saveCategory(expected);

        assertNotNull(actual);
    }

    @Test
    void shouldReturnDataWithoutError_whenResponseIsCorrect() throws Exception {
        String expected = "testCategoryName";

        CategoryWithNews input = CategoryWithNews.builder().name(expected).build();

        FullCategoryModel categoryToSave = FullCategoryModel.builder()
                .name(expected)
                .build();

        when(mapper.categoryWithNewsToFullCategoryModel(input))
                .thenReturn(categoryToSave);

        FullCategoryModel savedCategory = FullCategoryModel.builder()
                .name(expected)
                .id(1L)
                .news(new ArrayList<>())
                .build();
        when(storage.createCategory(categoryToSave))
                .thenReturn(Optional.of(savedCategory));

        when(mapper.fullCategoryToCategoryWithNews(savedCategory))
                .thenReturn(CategoryWithNews.builder()
                        .name(expected)
                        .news(new ArrayList<>())
                        .id(1L)
                        .build());

        ApplicationModel<CategoryWithNews, CategoryError> result = service.saveCategory(input);

        assertAll(() -> {
            assertFalse(result.hasError());
            assertEquals(expected, result.getData().getName());
        });

        verify(mapper, times(1)).categoryWithNewsToFullCategoryModel(input);
        verify(storage, times(1)).createCategory(categoryToSave);
        verify(mapper, times(1)).fullCategoryToCategoryWithNews(savedCategory);
    }

    @Test
    void shouldReturnError_whenStorageReturnEmptyOptional() throws Exception {
        String expected = "testCategory";
        CategoryWithNews input = CategoryWithNews.builder().name(expected).build();

        FullCategoryModel categoryToSave = FullCategoryModel.builder()
                .name(expected)
                .build();

        when(mapper.categoryWithNewsToFullCategoryModel(input))
                .thenReturn(categoryToSave);

        when(storage.createCategory(categoryToSave))
                .thenReturn(Optional.empty());

        ApplicationModel<CategoryWithNews, CategoryError> actual = service.saveCategory(input);

        assertAll(() -> {
            assertNotNull(actual);
            assertTrue(actual.hasError());
            assertNotNull(actual.getError());
        });

        verify(mapper, times(1)).categoryWithNewsToFullCategoryModel(input);
        verify(storage, times(1)).createCategory(categoryToSave);
    }

    @Test
    void shouldReturnApplicationModel_whenStorageUpdateCategory() throws Exception {
        String name = "test name";
        long id = 1L;

        CategoryWithNews category2update = CategoryWithNews.builder()
                .id(id)
                .name(name)
                .news(new ArrayList<>())
                .build();

        FullCategoryModel model2update = FullCategoryModel.builder()
                .id(id)
                .name(name)
                .news(new ArrayList<>())
                .build();
        when(mapper.categoryWithNewsToFullCategoryModel(category2update)).thenReturn(model2update);

        FullCategoryModel updatedModel = FullCategoryModel.builder()
                .id(1L)
                .news(List.of(
                        NewsModel.builder().id(1).build(),
                        NewsModel.builder().id(2).build(),
                        NewsModel.builder().id(3).build()
                        ))
                .name(name)
                .build();
        when(storage.updateCategory(model2update)).thenReturn(Optional.of(updatedModel));

        CategoryWithNews expected = CategoryWithNews.builder()
                .news(List.of(
                        News.builder().id(1L).build(),
                        News.builder().id(2L).build(),
                        News.builder().id(3L).build()
                ))
                .name(name)
                .id(id)
                .build();
        when(mapper.fullCategoryToCategoryWithNews(updatedModel)).thenReturn(expected);

        ApplicationModel<CategoryWithNews, CategoryError> actual = service.updateCategory(category2update);

        assertAll(() -> {
            assertNotNull(actual);
            assertFalse(actual.hasError());
            assertEquals(expected, actual.getData());
        });

        verify(mapper, times(1)).categoryWithNewsToFullCategoryModel(category2update);
        verify(storage, times(1)).updateCategory(model2update);
        verify(mapper, times(1)).fullCategoryToCategoryWithNews(updatedModel);
    }

    @Test
    void shouldReturnEmptyOptional_whenCategoryNotFound() throws Exception {
        String name = "test name";
        long id = 1L;

        CategoryWithNews category2update = CategoryWithNews.builder()
                .id(id)
                .name(name)
                .news(new ArrayList<>())
                .build();

        FullCategoryModel model2update = FullCategoryModel.builder()
                .id(id)
                .name(name)
                .news(new ArrayList<>())
                .build();
        when(mapper.categoryWithNewsToFullCategoryModel(category2update)).thenReturn(model2update);

        when(storage.updateCategory(model2update)).thenReturn(Optional.empty());

        ApplicationModel<CategoryWithNews, CategoryError> actual = service.updateCategory(category2update);

        assertAll(() -> {
            assertNotNull(actual);
            assertTrue(actual.hasError());
            assertNotNull(actual.getError());
            assertTrue(actual.getError().getMessage().contains(String.valueOf(id)));
        });
    }

    @Test
    void shouldDeleteCategoryById() throws Exception {
        long id = 1L;

        service.deleteCategory(id);

        verify(storage, times(1)).deleteCategory(id);
    }

    @NotNull
    private static List<Category> createCategoryList(long count) {
        ArrayList<Category> list = new ArrayList<>();

        for (long i = 0; i < count; i++) {
            list.add(createCategory(i, "test category " + i));
        }

        return list;
    }

    private static Category createCategory(long id, String name) {
        return Category.builder().id(id).name(name).newsCount(id).build();
    }

    private CategoryListModel createCategoryListModel(long count) {
        ArrayList<CategoryModel> list = new ArrayList<>();
        for (long i = 0; i < count; i++) {
            createCategoryModel(i, "test category " + i);
        }
        return CategoryListModel.builder()
                .count(count)
                .categories(list)
                .build();
    }

    private CategoryModel createCategoryModel(long id, String name) {
        return CategoryModel.builder()
                .id(id)
                .name(name)
                .newsCount(id)
                .build();
    }

    private CategoryFilter createFilter(int pageSize, int pageNumber) {
        CategoryFilter filter = new CategoryFilter();
        filter.setPageSize(pageSize);
        filter.setPageNumber(pageNumber);

        return filter;
    }
}