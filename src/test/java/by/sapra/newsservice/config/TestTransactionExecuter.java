package by.sapra.newsservice.config;

import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

public class TestTransactionExecuter {
    @Transactional(propagation = REQUIRES_NEW)
    public void execute(TransactionTestHelper helper) {
        helper.execute();
    }
}
