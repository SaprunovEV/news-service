package by.sapra.newsservice.storages.mappers;

import by.sapra.newsservice.models.CategoryEntity;
import by.sapra.newsservice.storages.models.CategoryListModel;
import by.sapra.newsservice.storages.models.CategoryModel;
import by.sapra.newsservice.storages.models.FullCategoryModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.sapra.newsservice.testUtils.CategoryTestDataBuilder.aCategory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperConf.class)
class StorageCategoryMapperTest {

    @Autowired
    StorageCategoryMapper mapper;

    @MockBean
    StorageCategory2NewsMapper category2NewsMapper;

    @Test
    void shouldMapEntityToCategoryModel() throws Exception {
        CategoryEntity expected = createEntity(1L, "name");

        CategoryModel actual = mapper.entityToCategoryModel(expected, expected.getCategory2News().size());

        assertCategoryModel(expected, actual);
    }

    @Test
    void shouldMapEntityListToListToCategoryListModel() throws Exception {
        List<CategoryEntity> expected = createEntityList(5);

        Map<Long, Long> countMap = expected.stream().collect(Collectors.toMap(CategoryEntity::getId, (e) -> (long) e.getCategory2News().size()));
        CategoryListModel actual = mapper.entityListToCategoryListModel(
                expected,
                countMap);

        assertCategoryListModel(expected, actual);
    }

    @Test
    void shouldMapEntityToFullCategory() throws Exception {
        CategoryEntity expected = aCategory().build();
        expected.setId(1L);

        when(category2NewsMapper.linksToNewsModelList(any())).thenReturn(new ArrayList<>());

        FullCategoryModel actual = mapper.entityToFullCategory(expected);

        assertFullCategory(expected, actual);

        verify(category2NewsMapper, times(1)).linksToNewsModelList(any());
    }

    @Test
    void shouldDoSomething() throws Exception {
        String name = "testCategoryName";
        FullCategoryModel expected = FullCategoryModel.builder()
                .news(new ArrayList<>())
                .name(name)
                .id(1L)
                .build();

        CategoryEntity actual = mapper.fullCategoryModelToEntity(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName().toLowerCase(), actual.getName());
            assertNotNull(actual.getCategory2News());
        });
    }

    private void assertFullCategory(CategoryEntity expected, FullCategoryModel actual) {
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getCategory2News().size(), actual.getNews().size());
        });
    }

    private void assertCategoryListModel(List<CategoryEntity> expected, CategoryListModel actual) {
        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getCategories());
            assertEquals(expected.size(), actual.getCount(),"size and count should be equals");
            for (int i = 0; i < actual.getCategories().size(); i++) {
                assertCategoryModel(expected.get(i), actual.getCategories().get(i));
            }
        });
    }

    private List<CategoryEntity> createEntityList(int count) {
        ArrayList<CategoryEntity> list = new ArrayList<>();
        for (long i = 0; i < count; i++) {
            list.add(
                    createEntity(i, "name " + i)
            );
        }
        return list;
    }

    private void assertCategoryModel(CategoryEntity expected, CategoryModel actual) {
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getCategory2News().size(), actual.getNewsCount());
            assertEquals(expected.getName(), actual.getName());
        });
    }

    private CategoryEntity createEntity(long id, String name) {
        CategoryEntity build = aCategory()
                .withName(name)
                .build();
        build.setId(id);
        return build;
    }
}