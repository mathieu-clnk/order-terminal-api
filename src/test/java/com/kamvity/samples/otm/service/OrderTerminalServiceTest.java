package com.kamvity.samples.otm.service;

import com.kamvity.samples.otm.config.OrderTerminalConfig;
import com.kamvity.samples.otm.entity.OrderTerminal;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = OrderTerminalConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderTerminalServiceTest {

    @Autowired
    private OrderTerminalService orderTerminalService;
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
    //@Sql("/new-customer.sql")
    @Sql("/new-terminal.sql")
    public void createOrder() {
        mockCustomerGetByIdOK();
        String[] terminals = { "202301012" };
        OrderTerminal orderTerminal = orderTerminalService.createOrder("123456",terminals).getBody();
        assertEquals(Long.decode("123456"),orderTerminal.getCustomerId());
    }

    @Test
    @Sql("/new-order-terminal.sql")
    public void getOrderById() {
        OrderTerminal orderTerminal = orderTerminalService.getOrderTerminalBy(Long.decode("123456")).getBody().getResult();
        assertEquals(Long.decode("123456"),orderTerminal.getCustomerId());
    }
}
