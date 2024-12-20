package com.justine.projectmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class CreateTicketDto {
    @NotBlank(message = "Title is required")
    @NotNull(message = "Title is required")
    @Getter @Setter
    private String title;

    @NotBlank(message = "Description is required")
    @NotNull(message = "Description is required")
    @Getter @Setter
    private String description;
}
