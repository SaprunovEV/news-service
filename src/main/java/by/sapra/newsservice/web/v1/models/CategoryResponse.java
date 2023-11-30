package by.sapra.newsservice.web.v1.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryResponse {
    private long id;
    private String name;
    private List<NewsItem> news;
}
