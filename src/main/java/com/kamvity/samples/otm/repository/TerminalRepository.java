package com.kamvity.samples.otm.repository;

import com.kamvity.samples.otm.entity.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal,Long> {
}
