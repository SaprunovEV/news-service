package by.sapra.newsservice.testUtils;

import by.sapra.newsservice.models.CommentEntity;
import by.sapra.newsservice.models.NewsEntity;
import by.sapra.newsservice.models.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class CommentTestDataBuilder implements TestDataBuilder<CommentEntity> {
    private String body = "test body of comment";

    private TestDataBuilder<CommentEntity> parent = null;
    private List<TestDataBuilder<CommentEntity>> children = new ArrayList<>();

    private TestDataBuilder<UserEntity> user = UserEntity::new;
    private TestDataBuilder<NewsEntity> news = NewsEntity::new;

    private CommentTestDataBuilder() {
    }

    private CommentTestDataBuilder(String body, TestDataBuilder<CommentEntity> parent, List<TestDataBuilder<CommentEntity>> children, TestDataBuilder<UserEntity> user, TestDataBuilder<NewsEntity> news) {
        this.body = body;
        this.parent = parent;
        this.children = children;
        this.user = user;
        this.news = news;
    }

    public static CommentTestDataBuilder aComment() {
        return new CommentTestDataBuilder();
    }

    public CommentTestDataBuilder withBody(String body) {
        return this.body == body ? this : new CommentTestDataBuilder(
                body, this.parent, this.children, this.user, this.news
        );
    }

    public CommentTestDataBuilder withParent(TestDataBuilder<CommentEntity> parent) {
        return this.parent == parent ? this : new CommentTestDataBuilder(
                this.body, parent, this.children, this.user, this.news
        );
    }

    public CommentTestDataBuilder withChildren(List<TestDataBuilder<CommentEntity>> children) {
        return this.children == children ? this : new CommentTestDataBuilder(
                this.body, this.parent, children, this.user, this.news
        );
    }

    public CommentTestDataBuilder withUser(TestDataBuilder<UserEntity> user) {
        return this.user == user ? this : new CommentTestDataBuilder(
                this.body, this.parent, this.children, user, this.news
        );
    }

    public CommentTestDataBuilder withNews(TestDataBuilder<NewsEntity> news){
        return this.news == news ? this : new CommentTestDataBuilder(
                this.body, this.parent, this.children, this.user, news
        );
    }

    @Override
    public CommentEntity build() {
        CommentEntity entity = new CommentEntity();
        entity.setBody(body);

        if (parent == null)
            entity.setParent(null);
        else {
            CommentEntity parentEntity = parent.build();
            parentEntity.getChildren().add(entity);
            entity.setParent(parentEntity);
        }

        entity.setChildren(getEntityCollection(children, c -> c.setParent(entity)));

        NewsEntity newsEntity = news.build();
        newsEntity.getComments().add(entity);
        entity.setNews(newsEntity);

        UserEntity userEntity = user.build();
        userEntity.getComments().add(entity);
        entity.setUser(userEntity);

        return entity;
    }
}
