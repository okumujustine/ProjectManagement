package com.justine.projectmanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class AddcommentDto {
    @Getter
    @Setter
    @NotNull(message = "Password is required")
    private String text;
}
