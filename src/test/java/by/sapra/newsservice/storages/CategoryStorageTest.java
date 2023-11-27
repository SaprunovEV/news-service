package by.sapra.newsservice.storages;

import by.sapra.newsservice.config.AbstractDataTest;
import by.sapra.newsservice.models.CategoryEntity;
import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.storages.mappers.StorageCategoryMapper;
import by.sapra.newsservice.storages.models.CategoryListModel;
import by.sapra.newsservice.storages.models.CategoryModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.sapra.newsservice.testUtils.CategoryTestDataBuilder.aCategory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = CategoryStorageConf.class)
class CategoryStorageTest extends AbstractDataTest {
    @Autowired
    private CategoryStorage storage;
    @Autowired
    private StorageCategoryMapper mapper;

    @Test
    void shouldNotReturnNull() throws Exception {
        CategoryFilter filter = createFilter(3, 0);

        CategoryListModel actual = storage.findAll(filter);

        assertNotNull(actual);
    }

    @Test
    void shouldReturnEmptyModelIfDatabaseWillEmpty() throws Exception {
        CategoryFilter filter = createFilter(3, 0);

        CategoryListModel actual = storage.findAll(filter);

        assertAll(() -> {
            assertEquals(0, actual.getCount());
            assertNotNull(actual.getCategories());
            assertTrue(actual.getCategories().isEmpty());
        });
    }

    @Test
    void shouldReturnAllCategories() throws Exception {
        CategoryFilter filter = createFilter(3, 0);

        List<CategoryEntity> entityList = List.of(
                getTestDbFacade().save(aCategory().withName("test 1")),
                getTestDbFacade().save(aCategory().withName("test 2")),
                getTestDbFacade().save(aCategory().withName("test 3")),
                getTestDbFacade().save(aCategory().withName("test 4"))
        );

        List<CategoryEntity> entities = entityList.subList(0, 3);

        Map<Long, Long> countMap = entities.stream()
                .collect(Collectors.toMap(CategoryEntity::getId, (c) -> (long) c.getCategory2News().size()));

        CategoryListModel expected = CategoryListModel.builder()
                .count(3)
                .categories(createListCategoryModel(entities, countMap))
                .build();
        when(mapper.entityListToCategoryListModel(entities, countMap)).thenReturn(
                expected
        );

        CategoryListModel actual = storage.findAll(filter);

        assertEquals(expected, actual);

        verify(mapper, times(1)).entityListToCategoryListModel(eq(entities), eq(countMap));
    }

    private List<CategoryModel> createListCategoryModel(List<CategoryEntity> expected, Map<Long, Long> countMap) {
        ArrayList<CategoryModel> list = new ArrayList<>();
        for (CategoryEntity categoryEntity : expected) {
            list.add(CategoryModel.builder()
                    .id(categoryEntity.getId())
                    .name(categoryEntity.getName())
                    .newsCount(countMap.get(categoryEntity.getId()))
                    .build());
        }
        return list;
    }

    private CategoryFilter createFilter(int pageSize, int pageNumber) {
        CategoryFilter filter = new CategoryFilter();
        filter.setPageSize(pageSize);
        filter.setPageNumber(pageNumber);

        return filter;
    }
}