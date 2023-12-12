package by.sapra.newsservice.services.mappers;
import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.storages.models.CategoryListModel;
import by.sapra.newsservice.storages.models.CategoryModel;
import by.sapra.newsservice.storages.models.FullCategoryModel;
import by.sapra.newsservice.storages.models.NewsModel;
import by.sapra.newsservice.services.models.CategoryWithNews;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperConf.class)
class CategoryModelMapperTest {

    @MockBean
    NewsModelMapper newsModelMapper;

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

    @Test
    void shouldMapFullCategoryModelToCategoryWithNews() throws Exception {
        long id = 1L;
        String name = "name";
        FullCategoryModel expected = createCategoryFullModel(id, name);

        CategoryWithNews actual = mapper.fullCategoryToCategoryWithNews(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getNews());
            assertEquals(3, actual.getNews().size());
            assertEquals(name, actual.getName());
            assertEquals(id, actual.getId());
        });
    }

    @Test
    void shouldMapCategoryWithNewsToFullCategoryModel() throws Exception {
        String expectedName = "testCategoryName";
        CategoryWithNews expected = CategoryWithNews.builder()
                .id(1L)
                .news(new ArrayList<>())
                .name(expectedName)
                .build();

        FullCategoryModel actual = mapper.categoryWithNewsToFullCategoryModel(expected);

        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getNews());
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
        });
    }

    private FullCategoryModel createCategoryFullModel(long id, String name) {

        return FullCategoryModel.builder()
                .id(id)
                .name(name)
                .news(List.of(
                        NewsModel.builder().build(),
                        NewsModel.builder().build(),
                        NewsModel.builder().build()
                ))
                .build();
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