package by.sapra.newsservice.web.v1.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsItem {
    private Long id;
    private String title;
    private String newsAbstract;
    private String body;
    private Long commentsCount;
}
