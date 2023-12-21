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
    private List<Long> categoryIds;
    private Long commentsCount;
    private Long owner;
    private List<Comment> comments;
}
