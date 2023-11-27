package by.sapra.newsservice.storages.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryModel {
    private long id;
    private String name;
    private long newsCount;
}
