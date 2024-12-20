package com.justine.projectmanagement.service;

import com.justine.projectmanagement.exceptions.EmployeeAlreadyAssignedException;
import com.justine.projectmanagement.exceptions.ResourceNotFoundException;
import com.justine.projectmanagement.model.Employee;
import com.justine.projectmanagement.model.Tickets;
import com.justine.projectmanagement.repository.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketsService {

    private final TicketsRepository ticketsRepository;
    private final EmployeeService employeeService;

    @Autowired
    public TicketsService(TicketsRepository ticketsRepository, EmployeeService employeeService) {
        this.ticketsRepository = ticketsRepository;
        this.employeeService = employeeService;
    }


    public Tickets createTicket(Tickets ticket) {
        return ticketsRepository.save(ticket);
    }


    public Tickets getTicketById(Long id) {
        return ticketsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + id + " not found!"));
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

    public Tickets assignToTicket(Long ticketId, String employeeEmail) {
        Tickets ticket = getTicketById(ticketId);
        Employee employee = employeeService.getEmployeeByEmail(employeeEmail);

        if (ticket.getAssignees().contains(employee)) {
            throw new EmployeeAlreadyAssignedException(
                    "Employee with email " + employeeEmail + " is already assigned to the ticket with ID " + ticketId + "."
            );
        }

        ticket.getAssignees().add(employee);

        return ticketsRepository.save(ticket);
    }
}
