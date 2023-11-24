package by.sapra.newsservice.storages.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryModel {
    private long id;
    private String name;
    private long newsCount;
    private List<NewsModel> news;
}
