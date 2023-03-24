package com.kamvity.samples.otm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customer")
public class CustomerDeprecated {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long customerId;

    @Column(length = 8,nullable = false)
    private String title;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(length = 512,nullable = false)
    private String address;

    @Column(name="zipcode",length = 16)
    private String zipCode;

}
