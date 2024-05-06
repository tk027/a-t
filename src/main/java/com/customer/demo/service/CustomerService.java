package com.customer.demo.service;

import com.customer.demo.model.entity.Customer;
import com.customer.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CustomerService {

    private static final Logger logger = getLogger(CustomerService.class);

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer create(Customer customer) {
        logger.debug("creating customer: {}", customer);
        return repository.save(customer);
    }

    public List<Customer> findAllByLastName(String lastName) {
        logger.debug("finding customers with last name: {}", lastName);
        return repository.findAllByLastName(lastName);
    }
}
