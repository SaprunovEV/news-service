package by.sapra.newsservice.models;

import by.sapra.newsservice.config.AbstractMigrationTest;
import org.junit.jupiter.api.Test;

import static by.sapra.newsservice.testUtils.CategoryTestDataBuilder.aCategory;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryEntityTest extends AbstractMigrationTest {
    @Test
    void shouldSaveTheEntityAndTakeId() throws Exception {
        CategoryEntity savedEntity = getTestDbFacade().save(aCategory());

        assertAll(() -> {
            assertNotNull(savedEntity.getId());
            assertNotNull(getTestDbFacade().find(savedEntity.getId(), CategoryEntity.class));
        });
    }
}