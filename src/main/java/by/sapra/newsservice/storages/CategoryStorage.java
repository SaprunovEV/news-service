package by.sapra.newsservice.storages;

import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.storages.models.CategoryListModel;
import by.sapra.newsservice.storages.models.FullCategoryModel;

import java.util.Optional;

public interface CategoryStorage {
    CategoryListModel findAll(CategoryFilter filter);

    Optional<FullCategoryModel> findById(long id);

    Optional<FullCategoryModel> createCategory(FullCategoryModel categoryToSave);

    Optional<FullCategoryModel> updateCategory(FullCategoryModel model2update);

    void deleteCategory(long id);
}
