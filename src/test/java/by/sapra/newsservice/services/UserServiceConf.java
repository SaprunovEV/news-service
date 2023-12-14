package by.sapra.newsservice.services;

import by.sapra.newsservice.services.impl.DatabaseUserService;
import by.sapra.newsservice.services.mappers.UserServiceMapper;
import by.sapra.newsservice.storages.UserStorage;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class UserServiceConf {
    @Bean
    public UserService service(UserStorage storage, UserServiceMapper mapper) {
        return new DatabaseUserService(storage, mapper);
    }
}
