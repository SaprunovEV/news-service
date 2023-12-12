package by.sapra.newsservice.web.v1.mappers;

import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.services.models.News;
import by.sapra.newsservice.services.models.CategoryWithNews;
import by.sapra.newsservice.web.v1.models.CategoryItem;
import by.sapra.newsservice.web.v1.models.CategoryListResponse;
import by.sapra.newsservice.web.v1.models.CategoryResponse;
import by.sapra.newsservice.web.v1.models.UpsertCategoryRequest;
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

    @Test
    void shouldMapFullCategoryModelToCategoryWithNews() throws Exception {
        long id = 1L;
        String name = "name";
        CategoryWithNews expected = createCategoryFullModel(id, name);

        CategoryResponse actual = mapper.categoryToCategoryResponse(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getNews());
            assertEquals(3, actual.getNews().size());
            assertEquals(name, actual.getName());
            assertEquals(id, actual.getId());
        });
    }

    @Test
    void shouldMapRequestToCategoryWithNews() throws Exception {
        UpsertCategoryRequest expected = UpsertCategoryRequest.builder()
                .name("testCategoryName")
                .build();

        CategoryWithNews actual = mapper.requestToCategoryWithNews(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getNews());
            assertEquals(expected.getName(), actual.getName());
        });
    }

    private CategoryWithNews createCategoryFullModel(long id, String name) {

        return CategoryWithNews.builder()
                .id(id)
                .name(name)
                .news(List.of(
                        News.builder().build(),
                        News.builder().build(),
                        News.builder().build()
                ))
                .build();
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