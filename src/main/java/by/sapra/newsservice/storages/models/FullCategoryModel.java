package by.sapra.newsservice.storages.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FullCategoryModel {
    private long id;
    private String name;
    private List<NewsModel> news;
}
