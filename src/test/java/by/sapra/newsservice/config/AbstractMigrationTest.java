package by.sapra.newsservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = AbstractMigrationConf.class)
public class AbstractMigrationTest extends AbstractDataTest{
    @Autowired
    private TestTransactionExecuter testTransactionExecuter;

    public TestTransactionExecuter getTestTransactionExecuter() {
        return testTransactionExecuter;
    }
}
