package by.sapra.newsservice.models;

import by.sapra.newsservice.config.AbstractMigrationTest;
import by.sapra.newsservice.testUtils.NewsTestDataBuilder;
import by.sapra.newsservice.testUtils.UserTestDataBuilder;
import net.bytebuddy.utility.RandomString;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.Test;

import static by.sapra.newsservice.testUtils.NewsTestDataBuilder.aNews;
import static by.sapra.newsservice.testUtils.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;

class NewsEntityTest extends AbstractMigrationTest {
    @Test
    void shouldSaveEntity() throws Exception {
        NewsEntity entity = getTestDbFacade()
                .save(
                        aNews()
                                .withUser(getTestDbFacade().persistedOnce(aUser())));

        assertAll(() -> {
            assertNotNull(entity);
            assertNotNull(entity.getId());
            assertNotNull(entity.getUser());
            assertNotNull(entity.getCreateAt());
            assertNotNull(entity.getUpdateAt());
        });
    }

    @Test
    void shouldThrowExceptionIfTitleIsNull() throws Exception {
        assertThrows(ConstraintViolationException.class, this::executeNullTitle);
    }

    @Test
    void shouldThrowExceptionIfTitleMoreThenPossible() throws Exception {
        assertThrows(DataException.class, this::executeLongTitle);
    }

    @Test
    void shouldThrowExceptionIfAbstractMoreThenPossible() throws Exception {
        assertThrows(DataException.class, this::executeLongAbstract);
    }

    @Test
    void shouldThrowExceptionIfBodyIsNull() throws Exception {
        assertThrows(ConstraintViolationException.class, this::executeNullBody);
    }

    private void executeNullBody() {
        getTestTransactionExecuter().execute(() -> {
            getTestDbFacade().save(
                    aNews()
                            .withUser(aUser())
                            .withBody(null)
            );
        });
    }

    private void executeLongAbstract() {
        getTestTransactionExecuter()
                .execute(
                        () -> getTestDbFacade().save(
                                aNews()
                                        .withNewsAbstract(RandomString.make(101))
                                        .withUser(getTestDbFacade().persistedOnce(aUser()))
                        )
                );
    }

    private void executeLongTitle() {
        getTestTransactionExecuter()
                .execute(
                        () -> getTestDbFacade().save(
                                aNews()
                                        .withTitle(RandomString.make(51))
                                        .withUser(getTestDbFacade().persistedOnce(aUser()))
                        )
                );
    }

    private void executeNullTitle() {
        getTestTransactionExecuter().execute(
                () ->
                        getTestDbFacade()
                                .save(
                                        aNews()
                                                .withUser(
                                                        getTestDbFacade().persistedOnce(aUser())
                                                )
                                                .withTitle(null)
                                )
        );
    }
}