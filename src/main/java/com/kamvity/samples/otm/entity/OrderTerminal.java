package com.kamvity.samples.otm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "orderTerminal", description = "Order of terminals")
public class OrderTerminal {

    /**
     * Identifier of the order.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Schema(name = "orderId",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    @Schema(name = "terminals",description = "List of terminal IDs.",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    @Schema(name = "customerEmail",pattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerEmail;
}
