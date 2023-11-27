package by.sapra.newsservice.web.v1.mappers;

import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.web.v1.models.CategoryItem;
import by.sapra.newsservice.web.v1.models.CategoryListResponse;
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
class CategoryMapperTest {
    @Autowired
    CategoryMapper mapper;

    @Test
    void shouldMapCategoryToCategoryItem() throws Exception {
        Category expected = createCategory(1L, "test category", 1L);

        CategoryItem actual = mapper.categoryToCategoryItem(expected);

        assertCategoryItem(expected, actual);
    }

    @Test
    void shouldMapCategoryListToCategoryListResponse() throws Exception {
        List<Category> expected = createCategoryList(4);

        CategoryListResponse actual = mapper.categoryItemListToCategoryListResponse(expected);

        assertCategoryListResponse(expected, actual);
    }

    private void assertCategoryListResponse(List<Category> expected, CategoryListResponse actual) {
        assertAll(() -> {
            assertNotNull(actual);
            for (int i = 0; i < actual.getCategories().size(); i++) {
                assertCategoryItem(expected.get(i), actual.getCategories().get(i));
            }
        });
    }

    private List<Category> createCategoryList(long count) {
        ArrayList<Category> list = new ArrayList<>();
        for (long i = 0; i < count; i++) {
            list.add(createCategory(
                    i, "category " + i, i
            ));
        }
        return list;
    }

    private void assertCategoryItem(Category expected, CategoryItem actual) {
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getNewsCount(), actual.getNewsCount());
            assertEquals(expected.getName(), actual.getName());
        });
    }

    private Category createCategory(long id, String name, long newsCount) {
        return Category.builder()
                .newsCount(newsCount)
                .name(name)
                .id(id)
                .build();
    }
}