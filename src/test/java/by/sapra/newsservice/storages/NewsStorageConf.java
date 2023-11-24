package by.sapra.newsservice.storages;

import by.sapra.newsservice.storages.impl.DatabaseNesStorage;
import by.sapra.newsservice.storages.mappers.StorageNewsMapper;
import by.sapra.newsservice.storages.reposytory.CommentRepository;
import by.sapra.newsservice.storages.reposytory.NewsRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class NewsStorageConf {
    @Bean
    public StorageNewsMapper storageNewsMapper() {
        return Mockito.mock(StorageNewsMapper.class);
    }
    @Bean
    public NewsStorage newsStorage(NewsRepository newsRepo, CommentRepository commRepo, StorageNewsMapper mapper) {
        return new DatabaseNesStorage(newsRepo, commRepo, mapper);
    }
}
