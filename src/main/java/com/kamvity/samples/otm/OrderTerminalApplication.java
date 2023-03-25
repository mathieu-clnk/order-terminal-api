package com.kamvity.samples.otm;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Mathieu Coavoux
 */
@SpringBootApplication
public class OrderTerminalApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderTerminalApplication.class,args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}