package com.justine.projectmanagement.controller;

import com.justine.projectmanagement.dto.CreateTicketDTO;
import com.justine.projectmanagement.model.Project;
import com.justine.projectmanagement.model.Tickets;
import com.justine.projectmanagement.service.ProjectService;
import com.justine.projectmanagement.service.TicketsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@Validated
public class TicketsController {

    private final TicketsService ticketsService;
    private final ProjectService projectService;

    @Autowired
    public TicketsController(TicketsService ticketsService, ProjectService projectService) {
        this.ticketsService = ticketsService;
        this.projectService = projectService;
    }


    @PostMapping
    public ResponseEntity<Tickets> createTicket(@Valid @RequestBody CreateTicketDTO ticket) {
        Tickets tick = new Tickets();
        tick.setTitle(ticket.getTitle());
        tick.setDescription(ticket.getDescription());

        Project proj = this.projectService.getProjectById(ticket.getProjectId());
        tick.setProject(proj);


        Tickets createdTicket = ticketsService.createTicket(tick);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Tickets> getTicketById(@PathVariable Long id) {
        Tickets ticket = ticketsService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }


    @GetMapping
    public ResponseEntity<List<Tickets>> getAllTickets() {
        List<Tickets> tickets = ticketsService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Tickets> updateTicket(@PathVariable Long id, @Valid @RequestBody Tickets ticketDetails) {
        Tickets updatedTicket = ticketsService.updateTicket(id, ticketDetails);
        return ResponseEntity.ok(updatedTicket);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketsService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
