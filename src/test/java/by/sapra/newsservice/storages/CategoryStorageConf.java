package by.sapra.newsservice.storages;

import by.sapra.newsservice.storages.impl.DatabaseCategoryStorage;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CategoryStorageConf {
    @Bean
    public CategoryStorage categoryStorage() {
        return new DatabaseCategoryStorage();
    }
}
