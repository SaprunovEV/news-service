package by.sapra.newsservice.web.v1.mappers;

import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.web.v1.models.CategoryListResponse;

import java.util.List;

public interface CategoryMapper {
    CategoryListResponse categoryItemListToCategoryListResponse(List<Category> list);
}
