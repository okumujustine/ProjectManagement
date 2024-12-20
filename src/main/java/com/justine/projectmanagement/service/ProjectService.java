package com.justine.projectmanagement.service;


import com.justine.projectmanagement.exceptions.ResourceNotFoundException;
import com.justine.projectmanagement.model.Project;
import com.justine.projectmanagement.model.Tickets;
import com.justine.projectmanagement.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project getProjectById(Long id) {
        return this.projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project with id " + id + "not found."));
    }
}
