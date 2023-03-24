package com.kamvity.samples.otm.controller;

import com.kamvity.samples.otm.entity.Terminal;
import com.kamvity.samples.otm.config.OrderTerminalConfig;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = OrderTerminalConfig.class)
//@ActiveProfiles("integration")
public class TerminalControllerTest {

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Sql("/new-terminal.sql")
    public void testGetTerminal() {
        String url = "http://localhost:" + port+"/v1/terminal/get-by-id?id=202301012";
        Terminal terminal = restTemplate.getForObject(url, Terminal.class);
        assertEquals("Cisco",terminal.getManufacturer());
    }
}
