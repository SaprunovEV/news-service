package by.sapra.newsservice.services.mappers;
import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.storages.models.CategoryListModel;
import by.sapra.newsservice.storages.models.CategoryModel;
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
class CategoryModelMapperTest {

    @Autowired
    public CategoryModelMapper mapper;

    @Test
    void shouldMapCategoryModelToCategory() throws Exception {
        CategoryModel expected = createCategoryModel(1L, "category", 1L);

        Category actual = mapper.categoryModelToCategory(expected);


        assertCategory(expected, actual);
    }

    @Test
    void shouldCategoryListModelToCategoryList() throws Exception {
        CategoryListModel expected = createCategoryListModel(4);

        List<Category> actual = mapper.categoryListModelToCategoryList(expected);

        assertCategoryList(expected, actual);
    }

    private void assertCategoryList(CategoryListModel expected, List<Category> actual) {
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getCategories().size(), actual.size());
            for (int i = 0; i < actual.size(); i++) {
                assertCategory(expected.getCategories().get(i), actual.get(i));
            }
        });
    }

    private CategoryListModel createCategoryListModel(int count) {
        ArrayList<CategoryModel> list = new ArrayList<>();
        for (long i = 0; i < count; i++) {
            list.add(
                    createCategoryModel(i, "name " + i, i)
            );
        }

        return CategoryListModel.builder()
                .count(count)
                .categories(list)
                .build();
    }

    private void assertCategory(CategoryModel expected, Category actual) {
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getNewsCount(), actual.getNewsCount());
            assertEquals(expected.getName(), actual.getName());
        });
    }

    private CategoryModel createCategoryModel(long id, String name, long newsCount) {
        return CategoryModel.builder()
                .newsCount(newsCount)
                .name(name)
                .id(id)
                .build();
    }
}