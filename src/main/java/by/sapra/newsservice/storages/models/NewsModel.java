package by.sapra.newsservice.storages.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsModel {
    private long id;
    private String title;
    private String newsAbstract;
    private String body;
    private long commentSize;
    private CommentListModel comments;
}
