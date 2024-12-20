package com.justine.projectmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class Tickets {
    @Id@GeneratedValue
    @Getter
    private Long id;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String description;

    @ManyToOne
    @Getter @Setter
    private Project project;

    @ManyToMany
    @Getter @Setter
    private List<Employee> assignees;

    @OneToMany(mappedBy = "ticket")
    private List<Comment> comments;
}
