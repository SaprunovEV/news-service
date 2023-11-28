package by.sapra.newsservice.services.impl;

import by.sapra.newsservice.models.errors.CategoryNotFound;
import by.sapra.newsservice.services.CategoryService;
import by.sapra.newsservice.services.mappers.CategoryModelMapper;
import by.sapra.newsservice.services.models.ApplicationModel;
import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.storages.CategoryStorage;
import by.sapra.newsservice.web.v1.controllers.CategoryWithNews;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseCategoryService implements CategoryService {
    private final CategoryStorage storage;
    private final CategoryModelMapper mapper;
    @Override
    public List<Category> findAll(CategoryFilter filter) {
        return mapper.categoryListModelToCategoryList(storage.findAll(filter));
    }

    @Override
    public ApplicationModel<CategoryWithNews, CategoryNotFound> findById(long id) {
        return null;
    }
}
