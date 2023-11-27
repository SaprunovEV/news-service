package by.sapra.newsservice.storages;

import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.storages.models.CategoryListModel;

public interface CategoryStorage {
    CategoryListModel findAll(CategoryFilter filter);
}
