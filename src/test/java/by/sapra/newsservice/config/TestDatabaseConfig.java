package by.sapra.newsservice.config;

import by.sapra.newsservice.testUtils.TestDbFacade;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestDatabaseConfig {

    @Bean
    public TestDbFacade testDbFacade() {
        return new TestDbFacade();
    }
}
