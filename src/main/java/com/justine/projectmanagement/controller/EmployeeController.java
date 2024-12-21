package com.justine.projectmanagement.controller;

import com.justine.projectmanagement.authentication.SecurityUtil;
import com.justine.projectmanagement.dto.AddEmployeeDto;
import com.justine.projectmanagement.dto.ApiResponseDto;
import com.justine.projectmanagement.dto.UpdateEmployeeDto;
import com.justine.projectmanagement.enums.EmployeeRole;
import com.justine.projectmanagement.exceptions.CreateCompanyException;
import com.justine.projectmanagement.exceptions.EmployeeAlreadyExistsException;
import com.justine.projectmanagement.model.Employee;
import com.justine.projectmanagement.repository.EmployeeRepository;
import com.justine.projectmanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/employees", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@Validated
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    SecurityUtil securityUtil;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<Employee>>> getEmployees() {
        List<Employee> employees = employeeService.getEmployees();
        ApiResponseDto<List<Employee>> response = new ApiResponseDto<>(employees, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{employeeId}/update")
    public ResponseEntity<ApiResponseDto<Employee>> updateEmployee(@PathVariable Long employeeId, @Valid @RequestBody UpdateEmployeeDto addEmployeeDto) {
        Employee employees = employeeService.updateEmployee(employeeId, addEmployeeDto);;
        ApiResponseDto<Employee> response = new ApiResponseDto<>(employees, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}/delete")
    public String deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployeeByEmail(employeeId);
        return "Employee with id " + employeeId + " was deleted successfully";
    }

    @PostMapping("/register")
    public Employee register(@RequestBody Employee employee) {
        employee.setPassword(encoder.encode(employee.getPassword()));
        return employeeService.register(employee);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<String>> login(@RequestBody Employee employee) {
        String token = employeeService.verify(employee);
        ApiResponseDto<String> response = new ApiResponseDto<>(token, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponseDto<Employee>> addEmployee(@RequestBody @Valid AddEmployeeDto emp) {

        Employee employee = new Employee();
        employee.setEmail(emp.getEmail());
        employee.setPassword(encoder.encode(emp.getPassword()));
        employee.setRole(emp.getRole());

        Employee newEmployee = employeeService.getEmployeeByEmailNoThrow(employee.getEmail());
        if(newEmployee != null) {
            throw new EmployeeAlreadyExistsException("Employee with email " + employee.getEmail() + " already exists");
        }

        Employee currentEmployee = employeeService.getEmployeeByEmail(securityUtil.getCurrentUser().getUsername());
        if(currentEmployee.getCompany() == null) {
            throw new CreateCompanyException("Create a company first, then you will be able to add employees");
        }

        employee.setCompany(currentEmployee.getCompany());
        Employee newlyAddedEmployee = employeeService.add(employee);

        ApiResponseDto<Employee> response = new ApiResponseDto<>(newlyAddedEmployee, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/role/{role}/company/{companyName}")
    public ResponseEntity<ApiResponseDto<List<Employee>>> getEmployeesByRoleAndCompany(@PathVariable EmployeeRole role,
                                                                                       @PathVariable String companyName) {
        List<Employee> employees = employeeService.getEmployeesByRoleAndCompany(role, companyName);
        ApiResponseDto<List<Employee>> response = new ApiResponseDto<>(employees, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
