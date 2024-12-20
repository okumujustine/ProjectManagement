package com.justine.projectmanagement.service;


import com.justine.projectmanagement.authentication.SecurityUtil;
import com.justine.projectmanagement.dto.CreateProjectDto;
import com.justine.projectmanagement.dto.CreateTicketDto;
import com.justine.projectmanagement.exceptions.ResourceNotFoundException;
import com.justine.projectmanagement.model.Company;
import com.justine.projectmanagement.model.Employee;
import com.justine.projectmanagement.model.Project;
import com.justine.projectmanagement.model.Tickets;
import com.justine.projectmanagement.repository.EmployeeRepository;
import com.justine.projectmanagement.repository.ProjectRepository;
import com.justine.projectmanagement.repository.TicketsRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final TicketsRepository ticketsRepository;
    private final SecurityUtil securityUtil;

    public ProjectService(ProjectRepository projectRepository, EmployeeRepository employeeRepository, TicketsRepository ticketsRepository, SecurityUtil securityUtil) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.ticketsRepository = ticketsRepository;
        this.securityUtil = securityUtil;
    }

    public Project getProjectById(Long id) {
        return this.projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project with id " + id + " not found."));
    }

    public Project createNewProject(CreateProjectDto project) {
        Employee employee = this.employeeRepository.findByEmail(securityUtil.getCurrentUser().getUsername());

        Project newProject = new Project();
        newProject.setName(project.getName());
        newProject.setDescription(project.getDescription());
        newProject.setCompany(employee.getCompany());

        return this.projectRepository.save(newProject);
    }

    @Transactional
    public Project createProjectTicket(@Valid CreateTicketDto ticketDto, Long projectId) {
        Project project = this.getProjectById(projectId);

        Tickets newTicket = new Tickets();
        newTicket.setTitle(ticketDto.getTitle());
        newTicket.setDescription(ticketDto.getDescription());
        newTicket.setProject(project);
        this.ticketsRepository.save(newTicket);

        return this.getProjectById(projectId);
    }
}
