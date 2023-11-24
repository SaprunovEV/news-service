package by.sapra.newsservice.storages;

import by.sapra.newsservice.config.AbstractDataTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = CategoryStorageConf.class)
class CategoryStorageTest extends AbstractDataTest {
    @Autowired
    private CategoryStorage storage;

}