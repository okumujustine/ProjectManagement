package com.justine.projectmanagement.dto;
import com.justine.projectmanagement.enums.EmployeeRole;
import com.justine.projectmanagement.model.Company;
import com.justine.projectmanagement.model.Employee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class AddEmployeeDto {
    @NotNull(message = "Email address is required")
    @Valid
    @Getter @Setter
    private String email;

    @NotNull(message = "Password is required")
    @Valid
    @Getter @Setter
    private String password;


    @NotNull(message = "ROLE is required")
    @Valid
    @Getter @Setter
    private EmployeeRole role;

}


