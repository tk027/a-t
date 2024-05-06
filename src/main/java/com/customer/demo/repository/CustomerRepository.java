package com.customer.demo.repository;

import com.customer.demo.model.entity.Customer;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CustomerRepository extends ListCrudRepository<Customer, Long> {

    List<Customer> findAllByLastName(String lastName);

}
