package by.sapra.newsservice.storages.mappers;

import by.sapra.newsservice.models.Category2News;
import by.sapra.newsservice.models.CategoryEntity;
import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.models.UserEntity;
import by.sapra.newsservice.storages.models.NewsListModel;
import by.sapra.newsservice.storages.models.NewsModel;
import by.sapra.newsservice.storages.reposytory.CommentRepository;
import by.sapra.newsservice.testUtils.UserTestDataBuilder;
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

import static by.sapra.newsservice.testUtils.CommentTestDataBuilder.aComment;
import static by.sapra.newsservice.testUtils.NewsTestDataBuilder.aNews;
import static by.sapra.newsservice.testUtils.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperConf.class)
class StorageNewsMapperTest {
    @Autowired
    StorageNewsMapper mapper;

    @MockBean
    CommentRepository commentRepository;


    @Test
    void shouldMapEntityToNewsModel() throws Exception {
        NewsEntity expected = createEntity(1L);

        NewsModel actual = mapper.entityToModelWithLinks(expected, (long) expected.getComments().size());

        assertNewsModel(expected, actual);
    }

    @Test
    void shouldDoSomething() throws Exception {
        List<NewsEntity> expected = createListNewsEntities(4);
        Map<Long, Long> countMap = createCountMap(expected);

        NewsListModel actual = mapper.entitiesListToNewsListModel(expected, countMap);

        assertNewsListModel(expected, countMap, actual);
    }

    private void assertNewsListModel(List<NewsEntity> expected, Map<Long, Long> countMap, NewsListModel actual) {
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.getCount());
            assertEquals(actual.getNews().size(), actual.getCount());
            for (int i = 0; i < actual.getCount(); i++) {
                assertNewsModel(expected.get(i), actual.getNews().get(i));
            }
        });
    }

    private Map<Long, Long> createCountMap(List<NewsEntity> expected) {
        return expected.stream().collect(Collectors.toMap(NewsEntity::getId, (e) -> (long) e.getComments().size()));
    }

    private static NewsEntity createEntity(long id) {
        UserTestDataBuilder userBuilder = aUser();
        UserEntity user = userBuilder.build();
        user.setId(1L);
        NewsEntity result = aNews()
                .withComments(
                        List.of(
                                aComment(),
                                aComment(),
                                aComment(),
                                aComment()
                        ))
                .withUser(userBuilder)
                .build();

        result.setId(id);
        result.setUser(user);

        Category2News category2News = new Category2News();
        category2News.setNews(result);
        CategoryEntity category = new CategoryEntity();
        category.setId(id);
        category2News.setCategory(category);
        result.setCategory2News(
                List.of(
                        category2News
                )
        );
        return result;
    }

    private List<NewsEntity> createListNewsEntities(int count) {
        ArrayList<NewsEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            createEntity(i);
        }
        return list;
    }

    private void assertNewsModel(NewsEntity expected, NewsModel actual) {
        assertAll(() -> {
            assertNotNull(actual, "actual should not be null");
            assertEquals(expected.getId(), actual.getId(), "ids should be equals");
            assertEquals(expected.getTitle(), actual.getTitle(), "title should be equals");
            assertEquals(expected.getNewsAbstract(), actual.getNewsAbstract(),"abstract should be equals");
            assertEquals(expected.getBody(), actual.getBody(), "body should be equals");
            assertEquals(expected.getComments().size(), actual.getCommentSize(),"comment count should be equals");
            assertNotNull(actual.getOwner(), "владелец не должен быть null");
            assertEquals(expected.getUser().getId(), actual.getOwner(), "должен установить владельца");
            expected.getCategory2News().stream().map(Category2News::getCategory).map(CategoryEntity::getId).forEach(
                    id -> assertTrue(actual.getCategoryIds().contains(id))
            );
        });
    }
}