package by.sapra.newsservice.services;

import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.web.v1.controllers.CategoryWithNews;

import java.util.List;

public interface CategoryService {
    List<Category> findAll(CategoryFilter filter);

    CategoryWithNews findById(long id);
}
