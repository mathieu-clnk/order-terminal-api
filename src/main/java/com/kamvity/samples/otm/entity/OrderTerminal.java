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

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long orderId;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Timestamp orderTimestamp;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Terminal> terminals;

    //@OneToOne
    //@JoinColumn(name="customerId",nullable = false)
    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private String customerEmail;
}
