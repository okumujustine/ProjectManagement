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
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @ManyToOne
    @Setter @Getter
    private Company company;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Tickets> ticket;

}
