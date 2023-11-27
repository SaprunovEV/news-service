package by.sapra.newsservice.services;

import by.sapra.newsservice.services.impl.DatabaseCategoryService;
import by.sapra.newsservice.services.mappers.CategoryModelMapper;
import by.sapra.newsservice.storages.CategoryStorage;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CategoryServiceConf {

    @Bean
    public CategoryStorage categoryStorage() {
        return Mockito.mock(CategoryStorage.class);
    }

    @Bean
    public CategoryModelMapper categoryModelMapper() {
        return Mockito.mock(CategoryModelMapper.class);
    }
    @Bean
    public CategoryService categoryService(CategoryStorage catRep, CategoryModelMapper mapper) {
        return new DatabaseCategoryService(catRep, mapper);
    }
}
