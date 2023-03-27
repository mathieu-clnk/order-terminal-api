package com.kamvity.samples.otm.response;

import com.kamvity.samples.otm.entity.OrderTerminal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "OrderTerminalResponse", description = "Response for OrderTerminal")
public class OrderTerminalResponse extends Response {

    @Schema(name = "result", implementation = OrderTerminal.class)
    public OrderTerminal result;
}
