package com.justine.projectmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {
    @Id@GeneratedValue
    private Long id;

    private String text;

    @ManyToOne
    private Tickets tickets;
}
