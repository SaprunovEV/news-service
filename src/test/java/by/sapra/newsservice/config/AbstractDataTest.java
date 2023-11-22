package by.sapra.newsservice.config;

import by.sapra.newsservice.testUtils.TestDbFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextHierarchy({
        @ContextConfiguration(
                initializers = PostgresInit.PostgresApplicationContextInitializer.class,
                classes = TestDatabaseConfig.class
        )
})
public abstract class AbstractDataTest {
    @Autowired
    private TestDbFacade testDbFacade;
    @Autowired
    private TestEntityManager entityManager;

    @BeforeAll
    static void beforeAll() {
        PostgresInit.container.start();
    }

    public TestDbFacade getTestDbFacade() {
        return testDbFacade;
    }

    public TestEntityManager getEntityManager() {
        return entityManager;
    }

    @AfterEach
    void tearDown() {
        testDbFacade.cleanDatabase();
    }
}
