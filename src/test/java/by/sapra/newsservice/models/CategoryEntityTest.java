package by.sapra.newsservice.models;

import by.sapra.newsservice.config.AbstractMigrationTest;
import org.hibernate.exception.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static by.sapra.newsservice.testUtils.CategoryTestDataBuilder.aCategory;
import static org.junit.jupiter.api.Assertions.*;

class CategoryEntityTest extends AbstractMigrationTest {
    @Test
    void shouldSaveTheEntityAndTakeId() throws Exception {
        CategoryEntity savedEntity = getTestDbFacade().save(aCategory());

        assertAll(() -> {
            assertNotNull(savedEntity.getId());
            assertNotNull(getTestDbFacade().find(savedEntity.getId(), CategoryEntity.class));
        });
    }

    @Test
    void shouldThrowExceptionIfNameIsNull() throws Exception {
        assertThrows(ConstraintViolationException.class, this::executeNullName);
    }

    private void executeNullName() {
        getTestTransactionExecuter().execute(() -> getTestDbFacade().save(aCategory().withName(null)));
    }
}