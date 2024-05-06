package com.customer.demo.controller;


import com.customer.demo.mapper.CustomerMapper;
import com.customer.demo.model.api.CustomerApiDto;
import com.customer.demo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.customer.demo.mapper.CustomerMapper.toApiDto;
import static com.customer.demo.mapper.CustomerMapper.toEntity;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CustomerApiDto> create(@RequestBody @Valid CustomerApiDto apiDto) {
        var customer = service.create(toEntity(apiDto));
        return new ResponseEntity<>(toApiDto(customer), CREATED);
    }

    @GetMapping
    public List<CustomerApiDto> findCustomers(@RequestParam String lastName) {
        return service.findAllByLastName(lastName)
                .stream()
                .map(CustomerMapper::toApiDto)
                .toList();
    }
}
