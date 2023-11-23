package by.sapra.newsservice.web.v1.models;

import jakarta.persistence.JoinColumn;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsItem {
    private Long id;
    private String title;
    @JoinColumn(name = "abstract")
    private String newsAbstract;
    private String body;
    private Long commentCount;
}
