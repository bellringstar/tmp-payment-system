package com.example.paymentapi.ticket.repository;

import com.example.paymentapi.ticket.entity.Ticket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.id = :id AND t.isDeleted = false")
    @Override
    Optional<Ticket> findById(Long id);

}