package com.justine.projectmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CreateTicketDTO {
    @NotBlank(message = "Title is required")
    @Getter @Setter
    private String title;

    @NotBlank(message = "Description is required")
    @Getter @Setter
    private String description;

    @NotNull(message = "Project ID is required")
    @Getter @Setter
    private Long projectId;
}
