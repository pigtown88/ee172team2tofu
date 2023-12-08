package com.ee172.team2.lpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ee172.team2.lpt.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
