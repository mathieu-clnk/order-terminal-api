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
@Table(name="terminal")
public class Terminal {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long terminalId;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String version;

    @Column(nullable = false,unique = true)
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name="orderId",nullable = true)
    private OrderTerminal orderTerminal;
}
