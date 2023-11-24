package by.sapra.newsservice.services;

import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.services.models.CategoryFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseCategoryService implements CategoryService {
    @Override
    public List<Category> findAll(CategoryFilter filter) {
        return null;
    }
}
