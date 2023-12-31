package by.sapra.newsservice.models;

import by.sapra.newsservice.config.AbstractMigrationTest;
import net.bytebuddy.utility.RandomString;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
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
        assertThrows(jakarta.validation.ConstraintViolationException.class, this::executeNullName);
    }

    @Test
    void shouldThrowExceptionIfNameIsMoreThenPossibly() throws Exception {
        assertThrows(DataException.class, this::executeMoreThenPossibly);
    }

    @Test
    void shouldThrowExceptionIfDatabaseHaveCategoryWithName() throws Exception {
        assertThrows(ConstraintViolationException.class, this::executeEqualsName);
    }

    @Test
    void shouldThrowExceptionIfDatabaseHaveCategoryWithNameIgnoreCase() throws Exception {
        assertThrows(ConstraintViolationException.class, this::executeEqualsNameIgnoreCase);
    }

    @Test
    void shouldThrowExceptionIfNameIsEmpty() throws Exception {
        assertThrows(jakarta.validation.ConstraintViolationException.class, this::executeEmptyName);
    }

    private void executeEmptyName() {
        getTestTransactionExecuter().execute(() -> {
            getTestDbFacade().save(aCategory().withName(""));
        });
    }

    private void executeEqualsNameIgnoreCase() {
        getTestTransactionExecuter()
                .execute(() -> {
                    String lowerCase = "test";
                    String upperCase = "TEST";
                    getTestDbFacade().save(aCategory().withName(lowerCase));
                    getTestDbFacade().save(aCategory().withName(upperCase));
                });
    }

    private void executeEqualsName() {
        getTestTransactionExecuter()
                .execute(() -> {
                    String name = "testName";
                    getTestDbFacade().save(aCategory().withName(name));
                    getTestDbFacade().save(aCategory().withName(name));
                });
    }

    private void executeMoreThenPossibly() {
        getTestTransactionExecuter()
                .execute(() -> getTestDbFacade().save(aCategory().withName(
                        RandomString.make(51))));
    }

    private void executeNullName() {
        getTestTransactionExecuter().execute(() -> getTestDbFacade().save(aCategory().withName(null)));
    }
}