package by.sapra.newsservice.storages.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewsModel {
    private long id;
    private String title;
    private String newsAbstract;
    private String body;
    private List<CommentModel> comments;
}
