package com.customer.demo;

import com.customer.demo.model.entity.Customer;
import com.customer.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

import static org.slf4j.LoggerFactory.getLogger;

@TestConfiguration(proxyBeanMethods = false)
public class TestDemoApplication {

    private static final Logger logger = getLogger(TestDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.from(DemoApplication::main).with(TestDemoApplication.class).run(args);
    }

    public static void bootstrapCustomers(CustomerRepository repository) {
        logger.debug("--- bootstrapping customers ---");
        repository.save(
                new Customer()
                        .setFirstName("Jason")
                        .setLastName("Bootstrapped")
                        .setDateOfBirth(LocalDate.of(1992, 1, 1))
        );
        repository.save(
                new Customer()
                        .setFirstName("Travis")
                        .setLastName("Bootstrapped")
                        .setDateOfBirth(LocalDate.of(1987, 10, 17))
        );
    }

    @Bean
    CommandLineRunner bootstrap(CustomerRepository repository) {
        return (args) -> bootstrapCustomers(repository);
    }
}