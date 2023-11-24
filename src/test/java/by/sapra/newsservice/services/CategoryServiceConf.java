package by.sapra.newsservice.services;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CategoryServiceConf {

    @Bean
    public CategoryService categoryService() {
        return new DatabaseCategoryService();
    }
}
