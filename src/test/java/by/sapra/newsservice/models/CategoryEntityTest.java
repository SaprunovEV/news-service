package by.sapra.newsservice.models;

import by.sapra.newsservice.config.AbstractDataTest;
import org.junit.jupiter.api.Test;

import static by.sapra.newsservice.testUtils.CategoryTestDataBuilder.aCategory;
import static org.junit.jupiter.api.Assertions.*;

class CategoryEntityTest extends AbstractDataTest {
    @Test
    void shouldSaveTheEntityAndTakeId() throws Exception {
        CategoryEntity savedEntity = getTestDbFacade().save(aCategory());

        assertAll(() -> {
            assertNotNull(savedEntity.getId());
            assertNotNull(getTestDbFacade().find(savedEntity.getId(), CategoryEntity.class));
        });
    }
}