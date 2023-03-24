package com.kamvity.samples.otm.repository;

import com.kamvity.samples.otm.entity.OrderTerminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTerminalRepository extends JpaRepository<OrderTerminal,Long> {

}
