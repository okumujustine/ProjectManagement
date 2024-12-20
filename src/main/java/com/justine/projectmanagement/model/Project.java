package com.justine.projectmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id@GeneratedValue
    @Getter
    private String id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "project")
    private List<Tickets> tickets;

}
