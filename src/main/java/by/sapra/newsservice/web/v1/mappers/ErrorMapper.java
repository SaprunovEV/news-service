package by.sapra.newsservice.web.v1.mappers;

import by.sapra.newsservice.models.errors.CategoryNotFound;
import by.sapra.newsservice.web.v1.models.ErrorResponse;

public interface ErrorMapper {
    ErrorResponse errorToCategoryErrorResponse(CategoryNotFound error);
}
