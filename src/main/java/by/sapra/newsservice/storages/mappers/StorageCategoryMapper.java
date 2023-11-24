package by.sapra.newsservice.storages.mappers;

import by.sapra.newsservice.models.CategoryEntity;
import by.sapra.newsservice.storages.models.CategoryListModel;

import java.util.List;
import java.util.Map;

public interface StorageCategoryMapper {
    CategoryListModel entityListToCategoryListModel(List<CategoryEntity> expected, Map<Long, Long> countMap);
}
