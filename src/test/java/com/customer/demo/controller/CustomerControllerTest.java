package com.customer.demo.controller;

import com.customer.demo.TestDemoApplication;
import com.customer.demo.model.api.CustomerApiDto;
import com.customer.demo.repository.CustomerRepository;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CustomerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository repository;

    private static String URL;

    @BeforeEach
    void setUp() {
        URL = "http://localhost:" + port + "/customers";
        repository.deleteAll();
    }

    @Test
    void create_shouldCreateCustomer() {
        var request = new CustomerApiDto()
                .setFirstName("test")
                .setLastName("user")
                .setDateOfBirth(LocalDate.of(1999, 1, 1));
        var response = restTemplate
                .withBasicAuth("admin", "admin123")
                .postForEntity(URL, request, CustomerApiDto.class);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        AssertionsForInterfaceTypes.assertThat(repository.findAll()).hasSize(1);
        assertThat(response.getBody())
                .matches(c -> c.getFirstName().equals(request.getFirstName())
                        && c.getLastName().equals(request.getLastName())
                        && c.getDateOfBirth().equals(request.getDateOfBirth()));
    }

    @Test
    void create_shouldFailOnUserAuthAndNoAuth() {
        var request = new CustomerApiDto()
                .setFirstName("test")
                .setLastName("user")
                .setDateOfBirth(LocalDate.of(1999, 1, 1));

        var withUserAuth = restTemplate
                .withBasicAuth("user1", "pass999")
                .postForEntity(URL, request, CustomerApiDto.class);
        assertThat(withUserAuth.getStatusCode()).isEqualTo(FORBIDDEN);

        var withNoAuth = restTemplate.postForEntity(URL, request, CustomerApiDto.class);
        assertThat(withNoAuth.getStatusCode()).isEqualTo(UNAUTHORIZED);
    }


    @Test
    void findCustomer_shouldFindCustomersWithLastName() {
        TestDemoApplication.bootstrapCustomers(repository);
        var response = restTemplate
                .withBasicAuth("user1", "pass999")
                .getForObject(URL + "?lastName=Bootstrapped", CustomerApiDto[].class);
        assertThat(response).hasSize(2)
                .allMatch(c -> c.getLastName().equals("Bootstrapped"));
    }

    @Test
    void findCustomer_shouldFailWithoutAuthentication() {
        var response = restTemplate.getForEntity(URL + "?lastName=Bootstrapped", CustomerApiDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(UNAUTHORIZED);
    }

}