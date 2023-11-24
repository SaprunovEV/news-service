package by.sapra.newsservice;

import by.sapra.newsservice.config.PostgresInit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresInit.PostgresApplicationContextInitializer.class})
class NewsServiceApplicationTests {

    @BeforeAll
    static void beforeAll() {
        PostgresInit.container.start();
    }

    @Test
    void contextLoads() {
    }

}
