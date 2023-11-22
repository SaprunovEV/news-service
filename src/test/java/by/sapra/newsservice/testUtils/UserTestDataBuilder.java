package by.sapra.newsservice.testUtils;

import by.sapra.newsservice.models.CommentEntity;
import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.models.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserTestDataBuilder implements TestDataBuilder<UserEntity> {
    private String name = "Test Name";
    private List<TestDataBuilder<NewsEntity>> news = new ArrayList<>();
    private List<TestDataBuilder<CommentEntity>> comments = new ArrayList<>();

    private UserTestDataBuilder() {}

    private UserTestDataBuilder(String name, List<TestDataBuilder<NewsEntity>> news, List<TestDataBuilder<CommentEntity>> comments) {
        this.name = name;
        this.news = news;
        this.comments = comments;
    }

    public static UserTestDataBuilder aUser() {
        return new UserTestDataBuilder();
    }

    public UserTestDataBuilder withName(String name) {
        return this.name == name ? this : new UserTestDataBuilder(name, this.news,this.comments);
    }

    public UserTestDataBuilder withNews(List<TestDataBuilder<NewsEntity>> news) {
        return this.news == news ? this : new UserTestDataBuilder(this.name, news, this.comments);
    }

    public UserTestDataBuilder withComments(List<TestDataBuilder<CommentEntity>> comments) {
        return this.comments == comments ? this : new UserTestDataBuilder(this.name, this.news, comments);
    }

    @Override
    public UserEntity build() {
        UserEntity entity = new UserEntity();
        entity.setName(this.name);
        entity.setNews(getEntityCollection(news, n -> n.setUser(entity)));
        entity.setComments(getEntityCollection(comments, c -> c.setUser(entity)));
        return entity;
    }
}
