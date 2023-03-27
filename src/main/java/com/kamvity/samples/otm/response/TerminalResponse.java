package com.kamvity.samples.otm.response;

import com.kamvity.samples.otm.entity.OrderTerminal;
import com.kamvity.samples.otm.entity.Terminal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "TerminalResponse", description = "Response for Terminal")
public class TerminalResponse extends Response{

    @Schema(name = "result", implementation = Terminal.class)
    public Terminal result;
}
