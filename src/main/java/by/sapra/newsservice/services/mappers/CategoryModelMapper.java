package by.sapra.newsservice.services.mappers;

import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.storages.models.CategoryListModel;
import by.sapra.newsservice.storages.models.CategoryModel;
import by.sapra.newsservice.storages.models.FullCategoryModel;
import by.sapra.newsservice.web.v1.controllers.CategoryWithNews;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE, uses = {NewsModelMapper.class})
public interface CategoryModelMapper {
    default List<Category> categoryListModelToCategoryList(CategoryListModel categoryListModel) {
        return categoryListModel.getCategories().stream().map(this::categoryModelToCategory).toList();
    }

    Category categoryModelToCategory(CategoryModel categoryModel);

    CategoryWithNews fullCategoryToCategoryWithNews(FullCategoryModel fullCategoryModel);
}
