package by.sapra.newsservice.models;

import by.sapra.newsservice.config.AbstractMigrationTest;
import by.sapra.newsservice.testUtils.UserTestDataBuilder;
import net.bytebuddy.utility.RandomString;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.Test;

import static by.sapra.newsservice.testUtils.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest extends AbstractMigrationTest {
    @Test
    void shouldGiveId() throws Exception {
        UserEntity savedEntity = getTestDbFacade().save(UserTestDataBuilder.aUser());

        assertAll(() -> {
            assertNotNull(savedEntity);
            assertNotNull(savedEntity.getId());

            assertEquals(savedEntity.getName(), getTestDbFacade().find(savedEntity.getId(), UserEntity.class).getName());
        });
    }

    @Test
    void shouldThrowExceptionIfNameIsNull() throws Exception {
        assertThrows(ConstraintViolationException.class, this::executeNullName);
    }

    @Test
    void shouldThrowExceptionIfLengthOfNameMoreThenPossible() throws Exception {
        assertThrows(DataException.class, this::executeLongName);
    }

    private void executeLongName() {
        getTestTransactionExecuter().execute(() -> getTestDbFacade().save(aUser().withName(RandomString.make(51))));
    }

    private void executeNullName() {
        getTestTransactionExecuter().execute(() -> getTestDbFacade().save(aUser().withName(null)));
    }
}