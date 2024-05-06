package com.customer.demo.mapper;

import com.customer.demo.model.api.CustomerApiDto;
import com.customer.demo.model.entity.Customer;

public class CustomerMapper {

    public static CustomerApiDto toApiDto(Customer customer) {
        return new CustomerApiDto()
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setDateOfBirth(customer.getDateOfBirth());
    }

    public static Customer toEntity(CustomerApiDto apiDto) {
        return new Customer()
                .setFirstName(apiDto.getFirstName())
                .setLastName(apiDto.getLastName())
                .setDateOfBirth(apiDto.getDateOfBirth());
    }

}