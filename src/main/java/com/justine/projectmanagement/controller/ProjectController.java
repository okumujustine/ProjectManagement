package com.justine.projectmanagement.controller;

import com.justine.projectmanagement.dto.ApiResponseDto;
import com.justine.projectmanagement.dto.CreateProjectDto;
import com.justine.projectmanagement.dto.CreateTicketDto;
import com.justine.projectmanagement.model.Project;
import com.justine.projectmanagement.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
@Validated
public class ProjectController {
    private final ProjectService projectService;
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<Project>> createProject(@RequestBody @Valid CreateProjectDto project) {
        Project newProject = this.projectService.createNewProject(project);
        return new ResponseEntity<>(new ApiResponseDto<>(newProject, HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @PostMapping(path = "/{projectId}/ticket", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponseDto<Project>> createProjectTicket(@RequestBody @Valid CreateTicketDto ticketDto, @PathVariable Long projectId) {
        Project newProject = this.projectService.createProjectTicket(ticketDto, projectId);
        return new ResponseEntity<>(new ApiResponseDto<>(newProject, HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }
}
