package com.justine.projectmanagement.controller;

import com.justine.projectmanagement.dto.CommentDto;
import com.justine.projectmanagement.dto.CreateTicketDto;
import com.justine.projectmanagement.jms.TicketNotificationProducer;
import com.justine.projectmanagement.model.Comment;
import com.justine.projectmanagement.model.Project;
import com.justine.projectmanagement.model.Tickets;
import com.justine.projectmanagement.service.ProjectService;
import com.justine.projectmanagement.service.TicketsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/tickets", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@Validated
public class TicketsController {

    private final TicketsService ticketsService;
    private final TicketNotificationProducer ticketNotificationProducer;

    @Autowired
    public TicketsController(TicketsService ticketsService, TicketNotificationProducer ticketNotificationProducer) {
        this.ticketsService = ticketsService;
        this.ticketNotificationProducer = ticketNotificationProducer;
    }


    @PostMapping
    public ResponseEntity<Tickets> createTicket(@Valid @RequestBody CreateTicketDto ticket) {
        Tickets tick = new Tickets();
        tick.setTitle(ticket.getTitle());
        tick.setDescription(ticket.getDescription());

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

    @PostMapping ("/{ticketId}/assign/{employeeEmail}")
    public ResponseEntity<Tickets> updateTicket(@PathVariable Long ticketId, @PathVariable String employeeEmail) {
        Tickets updatedTicket = ticketsService.assignToTicket(ticketId, employeeEmail);
        return ResponseEntity.ok(updatedTicket);
    }

    @PostMapping ("/{ticketId}/comment")
    public ResponseEntity<Comment> updateTicket(@RequestBody @Valid CommentDto commentDto, @PathVariable Long ticketId) {
        Comment updatedTicket = ticketsService.addToTicket(ticketId, commentDto.getText());
        ticketNotificationProducer.sendNotification(updatedTicket);
        return ResponseEntity.ok(updatedTicket);
    }

    @GetMapping("/stats")
    public List<Object[]> getTicketStatistics() {
        return ticketsService.getTicketStatsPerProjectAndRole();
    }
}
