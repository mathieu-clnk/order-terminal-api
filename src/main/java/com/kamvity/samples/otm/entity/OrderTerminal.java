package com.kamvity.samples.otm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kamvity.samples.cm.entity.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="order_terminal")
public class OrderTerminal {

    /**
     * Identifier of the order.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long orderId;

    /**
     * Total price of the order.
     */
    @Column(nullable = false)
    private Double price;

    /**
     * When the order has been submitted.
     */
    @Column(nullable = false)
    private Timestamp orderTimestamp;

    /**
     * Terminals ordered in this order.
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Terminal> terminals;

    /**
     * Customer who has placed the order.
     */
    //@OneToOne
    //@JoinColumn(name="customerId",nullable = false)
    @Column(nullable = false)
    private Long customerId;

    /**
     * The customer's email.
     */
    @Column(nullable = false)
    private String customerEmail;
}
