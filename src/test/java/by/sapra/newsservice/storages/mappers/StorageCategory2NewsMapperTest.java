package by.sapra.newsservice.storages.mappers;

import by.sapra.newsservice.config.AbstractDataTest;
import by.sapra.newsservice.models.Category2News;
import by.sapra.newsservice.models.CategoryEntity;
import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.models.UserEntity;
import by.sapra.newsservice.storages.models.NewsModel;
import by.sapra.newsservice.testUtils.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static by.sapra.newsservice.testUtils.Category2NewsTestDataBuilder.aCategory2News;
import static by.sapra.newsservice.testUtils.CategoryTestDataBuilder.aCategory;
import static by.sapra.newsservice.testUtils.NewsTestDataBuilder.aNews;
import static by.sapra.newsservice.testUtils.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ContextConfiguration(classes = MapperConf.class)
class StorageCategory2NewsMapperTest extends AbstractDataTest {

    @MockBean
    StorageNewsMapper newsMapper;

    @Autowired
    StorageCategory2NewsMapper mapper;

    @Test
    void shouldMapLinksToNews() throws Exception {
        TestDataBuilder<CategoryEntity> categoryBuilder = getTestDbFacade().persistedOnce(aCategory());
        TestDataBuilder<UserEntity> userBuilder = getTestDbFacade().persistedOnce(aUser());
        TestDataBuilder<NewsEntity> newsBuilder = getTestDbFacade().persistedOnce(aNews().withUser(userBuilder));

        Category2News expected = getTestDbFacade().save(
                aCategory2News()
                        .withNews(newsBuilder)
                        .withCategory(categoryBuilder)
        );

        mapper.lincToNewsModel(expected);

        verify(newsMapper, times(1)).entityToModelWithCategoryList(expected.getNews(), 0L);
    }

    @Test
    void shouldDoSomething() throws Exception {
        TestDataBuilder<UserEntity> userBuilder = getTestDbFacade().persistedOnce(aUser());

        List<Category2News> savedLinks = List.of(
                getTestDbFacade().save(
                        aCategory2News()
                                .withCategory(getTestDbFacade().persistedOnce(aCategory().withName("1")))
                                .withNews(getTestDbFacade().persistedOnce(aNews().withUser(userBuilder).withTitle("1")))
                ),
                getTestDbFacade().save(
                        aCategory2News()
                                .withCategory(getTestDbFacade().persistedOnce(aCategory().withName("2")))
                                .withNews(getTestDbFacade().persistedOnce(aNews().withUser(userBuilder).withTitle("2")))
                )
        );

        List<NewsModel> actual = mapper.linksToNewsModelList(savedLinks);

        assertEquals(savedLinks.size(), actual.size());

        savedLinks.forEach(saved ->
                verify(newsMapper, times(1)).entityToModelWithCategoryList(saved.getNews(), 0L));
    }
}