package by.sapra.newsservice.services.impl;

import by.sapra.newsservice.models.errors.CategoryError;
import by.sapra.newsservice.services.CategoryService;
import by.sapra.newsservice.services.mappers.CategoryModelMapper;
import by.sapra.newsservice.services.models.ApplicationModel;
import by.sapra.newsservice.services.models.Category;
import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.storages.CategoryStorage;
import by.sapra.newsservice.storages.models.FullCategoryModel;
import by.sapra.newsservice.services.models.CategoryWithNews;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

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
    public ApplicationModel<CategoryWithNews, CategoryError> findById(long id) {
        Optional<FullCategoryModel> optional = storage.findById(id);
        return new ApplicationModel<>() {
            @Override
            public CategoryWithNews getData() {
                return mapper.fullCategoryToCategoryWithNews(optional.get());
            }

            @Override
            public CategoryError getError() {
                return CategoryError.builder()
                        .message(MessageFormat.format("Категория с ID {0} не найдена!", id))
                        .build();
            }

            @Override
            public boolean hasError() {
                return optional.isEmpty();
            }
        };
    }

    @Override
    public  ApplicationModel<CategoryWithNews, CategoryError>  saveCategory(CategoryWithNews category) {
        Optional<FullCategoryModel> optional =
                storage.createCategory(mapper.categoryWithNewsToFullCategoryModel(category));
        return new ApplicationModel<>() {
            @Override
            public CategoryWithNews getData() {
                return mapper.fullCategoryToCategoryWithNews(optional.get());
            }

            @Override
            public CategoryError getError() {
                return null;
            }

            @Override
            public boolean hasError() {
                return optional.isEmpty();
            }
        };
    }
}
