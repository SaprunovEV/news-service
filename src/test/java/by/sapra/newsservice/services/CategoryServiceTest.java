package by.sapra.newsservice.services;

import by.sapra.newsservice.services.mappers.CategoryModelMapper;
import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.storages.CategoryStorage;
import by.sapra.newsservice.storages.models.CategoryListModel;
import by.sapra.newsservice.storages.models.CategoryModel;
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

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @NotNull
    private static List<Category> createCategoryList(long count) {
        ArrayList<Category> list = new ArrayList<>();

        for (long i = 0; i < count; i++) {
            list.add(createCategory(i, "test category " + i));
        }

        return list;
    }

    private static Category createCategory(long id, String name) {
        return Category.builder().id(id).name(name).newsCount(id).news(new ArrayList<>()).build();
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
                .news(new ArrayList<>())
                .build();
    }

    private CategoryFilter createFilter(int pageSize, int pageNumber) {
        CategoryFilter filter = new CategoryFilter();
        filter.setPageSize(pageSize);
        filter.setPageNumber(pageNumber);

        return filter;
    }
}