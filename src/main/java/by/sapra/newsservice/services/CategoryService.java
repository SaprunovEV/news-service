package by.sapra.newsservice.services;

import by.sapra.newsservice.models.errors.CategoryError;
import by.sapra.newsservice.services.models.ApplicationModel;
import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.services.models.CategoryWithNews;

import java.util.List;

public interface CategoryService {
    List<Category> findAll(CategoryFilter filter);

    ApplicationModel<CategoryWithNews, CategoryError> findById(long id);

    ApplicationModel<CategoryWithNews, CategoryError> saveCategory(CategoryWithNews category);

    CategoryWithNews updateCategory(CategoryWithNews news2update);
}
