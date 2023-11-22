package by.sapra.newsservice.testUtils;

import by.sapra.newsservice.models.Category2News;
import by.sapra.newsservice.models.CategoryEntity;
import by.sapra.newsservice.models.NewsEntity;

public class Category2NewsTestDataBuilder implements TestDataBuilder<Category2News> {
    private TestDataBuilder<CategoryEntity> category = CategoryEntity::new;
    private TestDataBuilder<NewsEntity> news = NewsEntity::new;

    private Category2NewsTestDataBuilder() {
    }

    private Category2NewsTestDataBuilder(TestDataBuilder<CategoryEntity> category, TestDataBuilder<NewsEntity> news) {
        this.category = category;
        this.news = news;
    }

    public static Category2NewsTestDataBuilder aCategory2News() {
        return new Category2NewsTestDataBuilder();
    }

    public Category2NewsTestDataBuilder withCategory(TestDataBuilder<CategoryEntity> category) {
        return this.category == category ? this : new Category2NewsTestDataBuilder(category, this.news);
    }

    public Category2NewsTestDataBuilder withNews(TestDataBuilder<NewsEntity> news) {
        return this.news == news ? this : new Category2NewsTestDataBuilder(this.category, news);
    }

    @Override
    public Category2News build() {
        Category2News entity = new Category2News();

        entity.setNews(news.build());
        entity.setCategory(category.build());

        return entity;
    }
}
