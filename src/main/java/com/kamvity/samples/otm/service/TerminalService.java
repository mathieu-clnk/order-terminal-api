package com.kamvity.samples.otm.service;

import com.kamvity.samples.otm.entity.Terminal;
import com.kamvity.samples.otm.repository.TerminalRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TerminalService {

    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private EntityManager entityManager;

    public ResponseEntity<Terminal> getTerminalById(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(terminalRepository.findById(id).get());
    }

    public ResponseEntity<Terminal> createTerminal(Terminal terminal) {
        return ResponseEntity.status(HttpStatus.OK).body(terminalRepository.save(terminal));
    }

}
