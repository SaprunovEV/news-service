package by.sapra.newsservice.web.v1.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpsertCategoryRequest {
    private String name;
}
