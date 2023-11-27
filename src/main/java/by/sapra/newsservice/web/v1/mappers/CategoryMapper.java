package by.sapra.newsservice.web.v1.mappers;

import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.web.v1.controllers.CategoryWithNews;
import by.sapra.newsservice.web.v1.models.CategoryItem;
import by.sapra.newsservice.web.v1.models.CategoryListResponse;
import by.sapra.newsservice.web.v1.models.CategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE, uses = {NewsMapper.class})
public interface CategoryMapper {
    default CategoryListResponse categoryItemListToCategoryListResponse(List<Category> list) {
        return CategoryListResponse.builder()
                .categories(list.stream().map(this::categoryToCategoryItem).toList())
                .build();
    }

    CategoryItem categoryToCategoryItem(Category category);

    CategoryResponse categoryToCategoryResponse(CategoryWithNews category);
}
