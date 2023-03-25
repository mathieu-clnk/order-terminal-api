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

    /**
     * Identifier of the terminal
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long terminalId;

    /**
     * The manufacturer of the terminal.
     */
    @Column(nullable = false)
    private String manufacturer;

    /**
     * The model name of the terminal.
     */
    @Column(nullable = false)
    private String model;

    /**
     * The version of the terminal.
     */
    @Column(nullable = false)
    private String version;

    /**
     * The serial number of the terminal.
     */
    @Column(nullable = false,unique = true)
    private String serialNumber;

    /**
     * The Identifier of the order if the terminal has been sold.
     */
    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name="orderId",nullable = true)
    private OrderTerminal orderTerminal;
}
