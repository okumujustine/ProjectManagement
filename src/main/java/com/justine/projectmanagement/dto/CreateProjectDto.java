package com.justine.projectmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

public class CreateProjectDto {
    @NotBlank(message = "Name is required")
    @NotNull(message = "Name is required")
    @Getter
    @Setter
    private String name;

    @NotBlank(message = "Description is required")
    @NotNull(message = "Description is required")
    @Getter @Setter
    private String description;
}
