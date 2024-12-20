package com.justine.projectmanagement.model;
import com.justine.projectmanagement.enums.EmployeeRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@DiscriminatorValue("EMPLOYEE")
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends Account {
    @Getter @Setter
    private EmployeeRole role;

    @ManyToMany
    private List<Tickets> tickets;

    @Override
    public String toString() {
        return "Employee{" +
                super.toString() +
                "role='" + role + '\'' +
                '}';
    }
}
