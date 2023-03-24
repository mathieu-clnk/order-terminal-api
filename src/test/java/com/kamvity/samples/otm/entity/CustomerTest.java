package com.kamvity.samples.otm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("dev")
public class CustomerTest {

    @Test
    public void testCustomerCreate() {
        CustomerDeprecated customer = new CustomerDeprecated();
        customer.setEmail("mat@s.com");
        customer.setAddress("12 st dol");
        customer.setFirstname("Mat");
        customer.setTitle("Mr");
        customer.setLastname("Y");
        customer.setZipCode("2222");
        assertEquals("mat@s.com",customer.getEmail());
    }
}
