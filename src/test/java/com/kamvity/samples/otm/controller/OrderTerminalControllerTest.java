package com.kamvity.samples.otm.controller;

import com.kamvity.samples.otm.config.OrderTerminalConfig;
import com.kamvity.samples.otm.entity.OrderTerminal;
import com.kamvity.samples.otm.response.OrderTerminalResponse;
import com.kamvity.samples.otm.service.CustomerService;
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
@ContextConfiguration(classes = OrderTerminalConfig.class)
//@ActiveProfiles("integration")
public class OrderTerminalControllerTest {
    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CustomerService customerService;

    @Test
    @Sql("/new-order-terminal.sql")
    public void testGetOrderId() {
        String url = "http://localhost:" + port+"/v1/order-terminal/get-by-id?orderId=123456";
        OrderTerminalResponse orderTerminal = restTemplate.getForObject(url,OrderTerminalResponse.class);
        assertEquals(Double.valueOf("200.00"),orderTerminal.getResult().getPrice());
    }
}
