package com.kamvity.samples.otm.controller;

import com.kamvity.samples.otm.entity.Terminal;
import com.kamvity.samples.otm.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/terminal")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    @GetMapping("/get-by-id")
    public ResponseEntity<Terminal> getTerminalById(@RequestParam Long id) {
        return terminalService.getTerminalById(id);
    }

    @PostMapping(path = "/create-terminal",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Terminal> createTerminal(@RequestBody Terminal terminal) {
        return terminalService.createTerminal(terminal);
    }
}
