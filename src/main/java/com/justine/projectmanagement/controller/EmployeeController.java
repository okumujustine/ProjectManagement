package com.justine.projectmanagement.controller;

import com.justine.projectmanagement.authentication.SecurityUtil;
import com.justine.projectmanagement.dto.AddEmployeeDto;
import com.justine.projectmanagement.dto.ApiResponseDto;
import com.justine.projectmanagement.exceptions.CreateCompanyException;
import com.justine.projectmanagement.exceptions.EmployeeAlreadyExistsException;
import com.justine.projectmanagement.model.Employee;
import com.justine.projectmanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
@Validated
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    SecurityUtil securityUtil;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping("/register")
    public Employee register(@RequestBody Employee employee) {
        employee.setPassword(encoder.encode(employee.getPassword()));
        return employeeService.register(employee);
    }

//    @PostMapping("/login")
//    public String login(@RequestBody Employee employee) {
//        return employeeService.verify(employee);
//    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<String>> login(@RequestBody Employee employee) {
        String token = employeeService.verify(employee);
        ApiResponseDto<String> response = new ApiResponseDto<>(token, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public Employee addEmployee(@RequestBody @Valid AddEmployeeDto emp) {

        Employee employee = new Employee();
        employee.setEmail(emp.getEmail());
        employee.setPassword(encoder.encode(emp.getPassword()));
        employee.setRole(emp.getRole());

        Employee newEmployee = employeeService.getEmployeeByEmail(employee.getEmail());
        if(newEmployee != null) {
            throw new EmployeeAlreadyExistsException("Employee with email " + employee.getEmail() + " already exists");
        }

        Employee currentEmployee = employeeService.getEmployeeByEmail(securityUtil.getCurrentUser().getUsername());
        if(currentEmployee.getCompany() == null) {
            throw new CreateCompanyException("Create a company first, then you will be able to add employees");
        }

        employee.setCompany(currentEmployee.getCompany());
        return employeeService.add(employee);
    }
}
