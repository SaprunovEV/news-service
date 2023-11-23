package by.sapra.newsservice.storages;

import by.sapra.newsservice.config.AbstractDataTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = NewsStorageConf.class)
class NewsStorageTest extends AbstractDataTest {
    @Autowired
    private NewsStorage newsStorage;
}