package by.sapra.newsservice.testUtils;

import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.models.UserEntity;

public class NewsTestDataBuilder implements TestDataBuilder<NewsEntity> {
    private String newsAbstract = "test abstract to news";
    private String body = "test body of news";
    private String title = "test title";
    private TestDataBuilder<UserEntity> user = UserEntity::new;

    private NewsTestDataBuilder(String newsAbstract, String body, String title, TestDataBuilder<UserEntity> user) {
        this.newsAbstract = newsAbstract;
        this.body = body;
        this.title = title;
        this.user = user;
    }

    private NewsTestDataBuilder() {
    }

    public static NewsTestDataBuilder aNews() {
        return new NewsTestDataBuilder();
    }

    public NewsTestDataBuilder withNewsAbstract(String newsAbstract) {
        return this.newsAbstract == newsAbstract ? this : new NewsTestDataBuilder(newsAbstract, this.body, this.title, this.user);
    }

    public NewsTestDataBuilder withBody(String body) {
        return this.body == body ? this : new NewsTestDataBuilder(this.newsAbstract, body, this.title, this.user);
    }

    public NewsTestDataBuilder withTitle(String title) {
        return this.title == title ? this : new NewsTestDataBuilder(this.newsAbstract, this.body, title, this.user);
    }

    public NewsTestDataBuilder withUser(TestDataBuilder<UserEntity> user) {
        return this.user == user ? this : new NewsTestDataBuilder(this.newsAbstract, this.body, this.title, user);
    }

    @Override
    public NewsEntity build() {
        NewsEntity entity = new NewsEntity();
        entity.setNewsAbstract(this.newsAbstract);
        entity.setBody(this.body);
        entity.setTitle(this.title);
        entity.setUser(user.build());
        return entity;
    }
}
