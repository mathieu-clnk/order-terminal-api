package com.kamvity.samples.otm.controller;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {OrderTerminalConfig.class})
//@ActiveProfiles("integration")
public class CustomerControllerTest {
    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate ;

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

    @Test
    @Sql("/new-customer.sql")
    public void testGetCustomer() {
        mockCustomerGetByIdOK();
        String url = "http://localhost:" + port+"/v1/customer/get-by-id?id=123456";
        ResponseCustomer responseCustomer = restTemplate.getForObject(url, ResponseCustomer.class);
        assertEquals("Marie",responseCustomer.getResult().getFirstname());
    }
}
