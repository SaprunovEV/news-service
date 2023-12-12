package by.sapra.newsservice.services.models;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CategoryWithNews {
    private long id;
    private String name;
    @Builder.Default
    private List<News> news = new ArrayList<>();
}
