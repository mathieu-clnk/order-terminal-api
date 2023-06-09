package com.kamvity.samples.otm.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kamvity.samples.otm.entity.OrderTerminal;
import com.kamvity.samples.otm.response.OrderTerminalResponse;
import com.kamvity.samples.otm.service.OrderTerminalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Order terminal controller.
 */
@RestController
@RequestMapping("/v1/order-terminal")
@Tag(name = "order-terminal-controller", description = "Order Terminal Controller")
public class OrderTerminalController {


    @Autowired
    private OrderTerminalService orderTerminalService;

    /**
     * Find terminal by id
     * @param orderId: the unique identifier of the order.
     * @return the order terminal.
     */
    @GetMapping("/get-by-id")
    @Operation(summary = "Get order by id.", description = "Get an order of terminals by its identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Get an order by its identifier", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderTerminalResponse.class),
                    examples = @ExampleObject(
                            name = "Success response",
                            value = "{"+
                                    "\"status\" : \"success\","+
                                    "\"errorMessage\" : \"\","+
                                    "\"sensitiveMessage\" : \"\","+
                                    "\"result\" : {"+
                                    "\"orderId\": \"123456\","+
                                    "\"price\" : \"200.00\","+
                                    "\"orderTimestamp\": \"20230328T10:00:00.000Z\","+
                                    "\"terminals\" : [ \"2222222\",\"333333\" ],"+
                                    "\"customerId\" : \"12356\","+
                                    "\"customerEmail\" : \"marie.curie@email.org\""+
                                    "}"+
                                    "}"
                    )
            ))
    })
    public ResponseEntity<OrderTerminalResponse> getOrderTerminalBy(
            @Parameter(name = "orderId",schema = @Schema(
                    implementation = String.class,
                    example = "888888"
            ))
            @RequestParam Long orderId) {
        return orderTerminalService.getOrderTerminalBy(orderId);
    }

    /**
     * Create a new order.
     * @param customerId: The unique identifier of the customer.
     * @param terminalIds: The list of terminal identifiers to order.
     * @return The order.
     */
    @GetMapping(path="/create-order",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Parameters(value = {
            @Parameter(name = "customerId", schema = @Schema(
                    implementation = String.class,
                    example = "888888"
            )),
            @Parameter(name = "terminalIds", schema = @Schema(
                    implementation = List.class,
                    example = "[ \"12345\", \"993223\" ]"
            ))

    })
    @Operation(summary = "Create order.", description = "Create a new order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Order created successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(ref = "#/components/schemas/OrderTerminal"),
                    examples = @ExampleObject(
                            name = "Success response",
                            value = "{"+
                                    "\"orderId\": \"123456\","+
                                    "\"price\" : \"200.00\","+
                                    "\"orderTimestamp\": \"20230328T10:00:00.000Z\","+
                                    "\"terminals\" : [ \"2222222\",\"333333\" ],"+
                                    "\"customerId\" : \"12356\","+
                                    "\"customerEmail\" : \"marie.curie@email.org\""+
                                    "}"
                    )
            ))
    })
    public ResponseEntity<OrderTerminal> createOrder(
            @JsonProperty String customerId,
            @JsonProperty String[] terminalIds) {
        return orderTerminalService.createOrder(customerId,terminalIds);

    }
}
