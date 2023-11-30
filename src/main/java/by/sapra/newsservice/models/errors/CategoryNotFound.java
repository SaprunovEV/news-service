package by.sapra.newsservice.models.errors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryNotFound {
    private String message;
}
