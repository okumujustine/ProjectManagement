package com.justine.projectmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class CommentDto {
    @NotBlank(message = "Comment text is required")
    @NotNull(message = "Comment text is required")
    @Getter
    @Setter
    private String text;
}
