package by.sapra.newsservice.testUtils;

import by.sapra.newsservice.models.*;

import java.util.ArrayList;
import java.util.List;

public class NewsTestDataBuilder implements TestDataBuilder<NewsEntity> {
    private String newsAbstract = "test abstract to news";
    private String body = "test body of news";
    private String title = "test title";
    private TestDataBuilder<UserEntity> user = UserEntity::new;
    private List<TestDataBuilder<Category2News>> category2News = new ArrayList<>();
    private List<TestDataBuilder<CommentEntity>> comments = new ArrayList<>();

    private NewsTestDataBuilder(String newsAbstract, String body, String title, TestDataBuilder<UserEntity> user, List<TestDataBuilder<Category2News>> category2News, List<TestDataBuilder<CommentEntity>> comments) {
        this.newsAbstract = newsAbstract;
        this.body = body;
        this.title = title;
        this.user = user;
        this.category2News = category2News;
        this.comments = comments;
    }

    private NewsTestDataBuilder() {
    }

    public static NewsTestDataBuilder aNews() {
        return new NewsTestDataBuilder();
    }

    public NewsTestDataBuilder withNewsAbstract(String newsAbstract) {
        return this.newsAbstract == newsAbstract ? this : new NewsTestDataBuilder(newsAbstract, this.body, this.title, this.user, this.category2News, this.comments);
    }

    public NewsTestDataBuilder withBody(String body) {
        return this.body == body ? this : new NewsTestDataBuilder(this.newsAbstract, body, this.title, this.user, this.category2News, this.comments);
    }

    public NewsTestDataBuilder withTitle(String title) {
        return this.title == title ? this : new NewsTestDataBuilder(this.newsAbstract, this.body, title, this.user, this.category2News, this.comments);
    }

    public NewsTestDataBuilder withUser(TestDataBuilder<UserEntity> user) {
        return this.user == user ? this : new NewsTestDataBuilder(this.newsAbstract, this.body, this.title, user, this.category2News, this.comments);
    }

    public NewsTestDataBuilder withComments(List<TestDataBuilder<CommentEntity>> comments) {
        return this.comments == comments ? this : new NewsTestDataBuilder(
                this.newsAbstract, this.body, this.title, this.user, this.category2News, this.comments
        );
    }

    @Override
    public NewsEntity build() {
        NewsEntity entity = new NewsEntity();
        entity.setNewsAbstract(this.newsAbstract);
        entity.setBody(this.body);
        entity.setTitle(this.title);
        UserEntity userEntity = user.build();
        userEntity.getNews().add(entity);
        entity.setUser(userEntity);
        entity.setCategory2News(getEntityCollection(category2News, c2n -> c2n.setNews(entity)));
        entity.setComments(getEntityCollection(comments, c -> c.setNews(entity)));
        return entity;
    }
}
