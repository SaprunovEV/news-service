package by.sapra.newsservice.storages.impl;

import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.storages.CategoryStorage;
import by.sapra.newsservice.storages.models.CategoryListModel;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCategoryStorage implements CategoryStorage {
    @Override
    public CategoryListModel findAll(CategoryFilter filter) {
        return null;
    }
}
