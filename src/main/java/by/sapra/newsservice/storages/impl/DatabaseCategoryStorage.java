package by.sapra.newsservice.storages.impl;

import by.sapra.newsservice.models.CategoryEntity;
import by.sapra.newsservice.services.models.CategoryFilter;
import by.sapra.newsservice.storages.CategoryStorage;
import by.sapra.newsservice.storages.mappers.StorageCategoryMapper;
import by.sapra.newsservice.storages.models.CategoryListModel;
import by.sapra.newsservice.storages.models.FullCategoryModel;
import by.sapra.newsservice.storages.reposytory.Category2NewsRepository;
import by.sapra.newsservice.storages.reposytory.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DatabaseCategoryStorage implements CategoryStorage {

    private final CategoryRepository repository;
    private final Category2NewsRepository category2NewsRepository;
    private final StorageCategoryMapper mapper;

    @Override
    public CategoryListModel findAll(CategoryFilter filter) {
        Page<CategoryEntity> page = repository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize()));

        return page.isEmpty() ? getEmptyModel() : mapListModel(page);
    }

    @Override
    public Optional<FullCategoryModel> findById(long id) {
        Optional<CategoryEntity> categoryOptional = repository.findById(id);
        return Optional.ofNullable(mapper.entityToFullCategory(categoryOptional.orElse(null)));
    }

    @Override
    public Optional<FullCategoryModel> createCategory(FullCategoryModel categoryToSave) {

        return Optional.empty();
    }

    private CategoryListModel mapListModel(Page<CategoryEntity> page) {
        Map<Long, Long> countMap = new HashMap<>();
        List<CategoryEntity> content = page.getContent();

        for (CategoryEntity entity : content) {
            countMap.put(entity.getId(), category2NewsRepository.countByCategory_Id(entity.getId()));
        }
        return mapper.entityListToCategoryListModel(content, countMap);
    }

    private CategoryListModel getEmptyModel() {
        return CategoryListModel.builder().categories(new ArrayList<>()).build();
    }
}
