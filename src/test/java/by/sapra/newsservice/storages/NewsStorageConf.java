package by.sapra.newsservice.storages;

import by.sapra.newsservice.storages.impl.DatabaseNesStorage;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class NewsStorageConf {
    @Bean
    public NewsStorage newsStorage() {
        return new DatabaseNesStorage();
    }
}
