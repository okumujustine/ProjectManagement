package com.justine.projectmanagement.service;

import com.justine.projectmanagement.exceptions.ResourceNotFoundException;
import com.justine.projectmanagement.model.Tickets;
import com.justine.projectmanagement.repository.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketsService {

    private final TicketsRepository ticketsRepository;

    @Autowired
    public TicketsService(TicketsRepository ticketsRepository) {
        this.ticketsRepository = ticketsRepository;
    }


    public Tickets createTicket(Tickets ticket) {
        return ticketsRepository.save(ticket);
    }


    public Tickets getTicketById(Long id) {
        return ticketsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + id));
    }


    public List<Tickets> getAllTickets() {
        return ticketsRepository.findAll();
    }

    public Tickets updateTicket(Long id, Tickets ticketDetails) {
        Tickets existingTicket = getTicketById(id);

        existingTicket.setTitle(ticketDetails.getTitle());
        existingTicket.setDescription(ticketDetails.getDescription());
        existingTicket.setProject(ticketDetails.getProject());
        existingTicket.setAssignees(ticketDetails.getAssignees());


        return ticketsRepository.save(existingTicket);
    }

    public void deleteTicket(Long id) {
        Tickets ticket = getTicketById(id);
        ticketsRepository.delete(ticket);
    }
}
