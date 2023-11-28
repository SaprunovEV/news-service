package by.sapra.newsservice.models.errors;

import by.sapra.newsservice.web.v1.models.ErrorResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryNotFound implements ErrorResponse {
    private String message;
}
