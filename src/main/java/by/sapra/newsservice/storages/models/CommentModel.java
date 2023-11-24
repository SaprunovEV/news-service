package by.sapra.newsservice.storages.models;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class CommentModel {
    private long id;
    private String body;
    private Instant createAt;
    private Instant updateAt;
    private List<CommentModel> children;
    private CommentModel parent;
    private UserModel user;
    private NewsModel news;
}
