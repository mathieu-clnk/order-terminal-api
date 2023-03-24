package com.kamvity.samples.otm.repository;

import com.kamvity.samples.otm.entity.OrderTerminal;
import com.kamvity.samples.otm.entity.Terminal;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class OrderTerminalRepositoryTest {

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

    @Autowired
    OrderTerminalRepository orderTerminalRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    TerminalRepository terminalRepository;

    @Test
    //@Sql("/new-customer.sql")
    @Sql("/new-terminal.sql")
    public void testSave() {
        mockCustomerGetByIdOK();
        OrderTerminal orderTerminal = new OrderTerminal();
        orderTerminal.setCustomerId(customerService.getById("123456").getBody().getResult().getId());
        orderTerminal.setCustomerEmail(customerService.getById("123456").getBody().getResult().getEmail());
        orderTerminal.setPrice(20.00);
        orderTerminal.setOrderTimestamp(Timestamp.from(Instant.now()));
        Terminal terminal = terminalRepository.findById(Long.decode("202301012")).get();
        List<Terminal> list = new ArrayList<Terminal>();
        list.add(terminal);
        orderTerminal.setTerminals(list);
        OrderTerminal orderTerminalResult = orderTerminalRepository.save(orderTerminal);
        assertEquals(20.00,orderTerminalResult.getPrice());
        assertEquals(Long.decode("202301012"),orderTerminalResult.getTerminals().get(0).getTerminalId());
        assertEquals(Long.decode("123456"),orderTerminalResult.getCustomerId());
    }
}
