package by.sapra.newsservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@TestConfiguration
@EnableTransactionManagement
public class AbstractMigrationConf {
   @Bean
   public TestTransactionExecuter testTransactionExecuter() {
       return new TestTransactionExecuter();
   }

}
