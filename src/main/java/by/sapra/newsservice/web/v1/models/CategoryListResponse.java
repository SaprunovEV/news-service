package by.sapra.newsservice.web.v1.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryListResponse {
    private List<CategoryItem> categories;
}
