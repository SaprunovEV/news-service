package by.sapra.newsservice.storages.models;

import by.sapra.newsservice.models.NewsEntity;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;

public class CommentListModel {
    private final NewsEntity news;
    private final Function<NewsEntity, List<CommentModel>> commentListBuilder;
    @Getter
    private final long count;

    public CommentListModel(NewsEntity news, Function<NewsEntity, List<CommentModel>> comments, long count) {
        this.news = news;
        this.commentListBuilder = comments;
        this.count = count;
    }

    public List<CommentModel> getCommentListBuilder() {
        return commentListBuilder.apply(news);
    }
}
