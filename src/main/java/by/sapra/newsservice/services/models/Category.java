package by.sapra.newsservice.services.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Category {
    private long id;
    private String name;
    private long newsCount;
    private List<News> news;
}
