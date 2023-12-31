package by.sapra.newsservice.services.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class News {
    private Long id;
    private String title;
    private String newsAbstract;
    private String body;
    private Long commentsCount;
    private List<Comment> comments;
}
