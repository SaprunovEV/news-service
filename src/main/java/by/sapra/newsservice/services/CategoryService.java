package by.sapra.newsservice.services;

import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.services.models.CategoryFilter;

import java.util.List;

public interface CategoryService {
    List<Category> findAll(CategoryFilter filter);
}
