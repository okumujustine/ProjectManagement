package com.justine.projectmanagement.dto;

import com.justine.projectmanagement.enums.EmployeeRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class UpdateEmployeeDto {
    @NotNull(message = "ROLE is required")
    @Valid
    @Getter @Setter
    private EmployeeRole role;
}
