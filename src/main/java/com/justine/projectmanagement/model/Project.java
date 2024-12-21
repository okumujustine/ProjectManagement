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
@NamedQueries({
        @NamedQuery(
                name = "Project.findByCompanyName",
                query = "SELECT p FROM Project p WHERE p.company.name = :companyName",
                lockMode = LockModeType.PESSIMISTIC_WRITE
        )
})
public class Project {
    @Id@GeneratedValue
    @Getter
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @Version
    private Long version;

    @ManyToOne
    @Setter @Getter
    private Company company;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Tickets> ticket;

}
