package by.sapra.newsservice.storages;

import by.sapra.newsservice.config.AbstractDataTest;
import by.sapra.newsservice.models.CategoryEntity;
import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.storages.mappers.MapperConf;
import by.sapra.newsservice.storages.mappers.StorageCategoryMapper;
import by.sapra.newsservice.storages.models.CategoryListModel;
import by.sapra.newsservice.storages.models.CategoryModel;
import by.sapra.newsservice.storages.models.FullCategoryModel;
import by.sapra.newsservice.testUtils.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.sapra.newsservice.testUtils.Category2NewsTestDataBuilder.aCategory2News;
import static by.sapra.newsservice.testUtils.CategoryTestDataBuilder.aCategory;
import static by.sapra.newsservice.testUtils.NewsTestDataBuilder.aNews;
import static by.sapra.newsservice.testUtils.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;

@ContextHierarchy({
        @ContextConfiguration(classes = MapperConf.class),
        @ContextConfiguration(classes = CategoryStorageConf.class)
})
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

        CategoryListModel actual = storage.findAll(filter);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyOptionalIfCategoryWillNotPresent() throws Exception {
        Optional<FullCategoryModel> actual = storage.findById(100L);

        assertTrue(actual.isEmpty());
    }


    @Test
    void shouldReturnCorrectCategory() throws Exception {

        CategoryEntity expected = getTestDbFacade().save(aCategory().withName("test 1"));

        getTestDbFacade().save(aCategory().withName("test 2"));
        getTestDbFacade().save(aCategory().withName("test 3"));
        getTestDbFacade().save(aCategory().withName("test 4"));

        Optional<FullCategoryModel> actual = storage.findById(expected.getId());

        assertAll(() -> {
            assertTrue(actual.isPresent());
            CategoryEntity category = getTestDbFacade().find(expected.getId(), CategoryEntity.class);
            assertEquals(category.getId(), actual.get().getId());
            assertEquals(category.getName(), actual.get().getName());
        });
    }

    @Test
    void shouldReturnNotEmptyOptional_whenCategoryIsCaved() throws Exception {

        String expected = "testName";

        FullCategoryModel categoryToSave = FullCategoryModel.builder().name(expected).news(new ArrayList<>()).build();

        Optional<FullCategoryModel> actual = storage.createCategory(categoryToSave);

        assertAll(() -> {
            assertTrue(actual.isPresent(), "неправильный ответ");
            assertEquals(expected.toLowerCase(), actual.get().getName());
            assertEquals(expected.toLowerCase(), getTestDbFacade().find(actual.get().getId(), CategoryEntity.class).getName(), "не сохранило");
        });
    }

    @Test
    void shouldReturnEmptyModel_whenCategoryNamAlreadyExist() throws Exception {
        String expected = "testName";

        getTestDbFacade().save(aCategory().withName(expected));

        FullCategoryModel categoryToSave = FullCategoryModel.builder().name(expected).news(new ArrayList<>()).build();

        Optional<FullCategoryModel> actual = storage.createCategory(categoryToSave);

        assertTrue(actual.isEmpty());
    }

    @Test
    void shouldReturnUpdatedEntity_whenCategoryIsNotExist() throws Exception {
        TestDataBuilder<CategoryEntity> categoryBuilder = getTestDbFacade().persistedOnce(
                aCategory()
                        .withName("firstName")
        );

        TestDataBuilder<NewsEntity> newsBuilder = getTestDbFacade().persistedOnce(
                aNews()
                        .withUser(getTestDbFacade().persistedOnce(aUser()))
        );

        getTestDbFacade().save(
                aCategory2News()
                        .withCategory(categoryBuilder)
                        .withNews(newsBuilder)
        );

        String name2update = "newname";
        CategoryEntity oldCategory = categoryBuilder.build();

        FullCategoryModel category2update = FullCategoryModel.builder()
                .name(name2update)
                .id(oldCategory.getId())
                .news(new ArrayList<>())
                .build();

        Optional<FullCategoryModel> actual = storage.updateCategory(category2update);

        assertAll(() -> {
            assertTrue(actual.isPresent(), "не представлена");
            FullCategoryModel model = actual.get();
            assertEquals(category2update.getName(), model.getName());
            assertNotNull(getTestDbFacade().find(oldCategory.getId(), CategoryEntity.class), "не правильно сохранил");
            CategoryEntity entity = getTestDbFacade().find(model.getId(), CategoryEntity.class);
            assertEquals(name2update, entity.getName());
            assertEquals(1, entity.getCategory2News().size());
        });
    }

    @Test
    void shouldReturnEmptyOptional_whenCategoryNotFound() throws Exception {
        getTestDbFacade().save(aCategory().withName("oldname"));

        FullCategoryModel category2update = FullCategoryModel.builder()
                .name("newname")
                .id(Long.MAX_VALUE)
                .news(new ArrayList<>())
                .build();

        Optional<FullCategoryModel> actual = storage.updateCategory(category2update);

        assertTrue(actual.isEmpty());
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