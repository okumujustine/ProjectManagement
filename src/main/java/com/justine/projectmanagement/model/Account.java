package com.justine.projectmanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="account_type")
public abstract class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="company_id")
    @Getter @Setter
    private Company company;


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", company=" + company +
                '}';
    }
}
