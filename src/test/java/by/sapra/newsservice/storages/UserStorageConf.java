package by.sapra.newsservice.storages;

import by.sapra.newsservice.storages.mappers.StorageUserMapper;
import by.sapra.newsservice.storages.reposytory.UserRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class UserStorageConf {

    @Bean
    public UserStorage storage(UserRepository repo, StorageUserMapper mapper) {
        return new DatabaseUserStorage(repo, mapper);
    }
}
