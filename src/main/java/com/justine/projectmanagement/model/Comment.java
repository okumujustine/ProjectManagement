package com.justine.projectmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id@GeneratedValue
    private Long id;

    @Setter @Getter
    private String text;

    @Version
    private Long version;

    @ManyToOne
    @Setter
    private Tickets ticket;

    public Comment(String text) {
        this.text = text;
    }
}
