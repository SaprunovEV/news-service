package by.sapra.newsservice.web.v1.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryItem {
    private long id;
    private String name;
    private long newsCount;
}
