package by.sapra.newsservice.storages;

import by.sapra.newsservice.storages.impl.DatabaseCategoryStorage;
import by.sapra.newsservice.storages.mappers.StorageCategoryMapper;
import by.sapra.newsservice.storages.reposytory.Category2NewsRepository;
import by.sapra.newsservice.storages.reposytory.CategoryRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CategoryStorageConf {
    @Bean
    public CategoryStorage categoryStorage(CategoryRepository catRepo, Category2NewsRepository ct2newRepo, StorageCategoryMapper mapper) {
        return new DatabaseCategoryStorage(catRepo, ct2newRepo, mapper);
    }
}
