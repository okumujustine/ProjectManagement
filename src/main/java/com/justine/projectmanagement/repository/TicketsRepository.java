package com.justine.projectmanagement.repository;

import com.justine.projectmanagement.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketsRepository extends JpaRepository<Tickets, Long> {
    @Query("SELECT p.name, e.role, COUNT(t) " +
            "FROM Tickets t " +
            "JOIN t.project p " +
            "JOIN t.assignees e " +
            "GROUP BY p.name, e.role " +
            "ORDER BY p.name, e.role")
    List<Object[]> findTicketStatsPerProjectAndRole();
}
