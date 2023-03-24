package com.kamvity.samples.otm.service;

import com.kamvity.samples.cm.entity.Customer;
import com.kamvity.samples.cm.entity.ResponseCustomer;
import com.kamvity.samples.otm.config.OrderTerminalConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {OrderTerminalConfig.class})
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    private static ClientAndServer mockServer;

    @BeforeAll
    public static void startServer() {
        mockServer = startClientAndServer(8081);
    }
    @BeforeEach
    public void reset() throws InterruptedException {
        mockServer.reset();
        //Make sure the Time limiter is passed
        Thread.sleep(2000);
        int i = 0;
        while ( ! mockServer.isRunning() && i < 30) {
            Thread.sleep(1000);
            i++;
        }
    }

    @AfterAll
    public static void stopServer() {
        mockServer.stop();
    }

    String customer1 = "{" +
                        "   \"status\" : \"success\"," +
                        "   \"errorMessage\" : \"\"," +
                        "   \"sensitiveMessage\" : \"\"," +
                        "   \"result\" : {" +
                        "                    \"id\" : \"123456\"," +
                        "                    \"title\" : \"Mrs\"," +
                        "                    \"firstname\" : \"Marie\"," +
                        "                    \"lastname\"  : \"Curie\"," +
                        "                    \"email\" : \"marie.curie@email.org\"" +
                        "                  }" +
                        "}";

    public void mockCustomerGetByIdOK() {
        Header header = Header.header("Content-Type","application/json");
        new MockServerClient("127.0.0.1",8081)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/api/v1/customers/get-by-id")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader(header)
                                .withBody(customer1)
                );
    }
    public void mockCustomerGetByEmailOK() {
        Header header = Header.header("Content-Type","application/json");
        new MockServerClient("127.0.0.1",8081)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/api/v1/customers/get-by-email")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader(header)
                                .withBody(customer1)
                );
    }

    public void mockCreateCustomerOK() {
        Header header = Header.header("Content-Type","application/json");
        new MockServerClient("127.0.0.1",8081)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/api/v1/customers/create")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader(header)
                                .withBody(customer1)
                );
    }
    @Test
    public void testCreateCustomer() {
        mockCreateCustomerOK();
        Customer customer = new Customer();
        customer.setEmail("marie.curie@email.org");
        customer.setLastname("Marie");
        customer.setTitle("Mrs");
        customer.setFirstname("Curie");
        ResponseEntity<ResponseCustomer> responseEntity = customerService.createCustomer(customer);
        Customer resultCustomer = responseEntity.getBody().getResult();
        assertEquals("marie.curie@email.org",resultCustomer.getEmail());
        assert resultCustomer.getId() > 0;
    }

    @Test
    //@Sql("/new-customer.sql")
    public void testFindCustomerByEmail() {
        mockCustomerGetByEmailOK();
        ResponseEntity<ResponseCustomer> responseEntity = customerService.getByEmail("marie.curie@email.org");
        Customer customer = responseEntity.getBody().getResult();
        assertEquals("marie.curie@email.org",customer.getEmail());
    }

    @Test
    public void testGetById() {
        mockCustomerGetByIdOK();
        ResponseEntity<ResponseCustomer> responseEntity = customerService.getById("123456");
        Objects.isNull(responseEntity);
        Customer customer = responseEntity.getBody().getResult();
        assertEquals("marie.curie@email.org",customer.getEmail());
    }
}
