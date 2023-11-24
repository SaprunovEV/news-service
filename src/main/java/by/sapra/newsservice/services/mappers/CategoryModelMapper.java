package by.sapra.newsservice.services.mappers;

import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.storages.models.CategoryListModel;

import java.util.List;

public interface CategoryModelMapper {
    List<Category> categoryListModelToCategoryList(CategoryListModel categoryListModel);
}
