package com.justine.projectmanagement.service;


import com.justine.projectmanagement.authentication.SecurityUtil;
import com.justine.projectmanagement.dto.AddEmployeeDto;
import com.justine.projectmanagement.dto.UpdateEmployeeDto;
import com.justine.projectmanagement.enums.EmployeeRole;
import com.justine.projectmanagement.exceptions.AuthenticationFailedException;
import com.justine.projectmanagement.exceptions.EmployeeAlreadyExistsException;
import com.justine.projectmanagement.exceptions.ForbiddenAction;
import com.justine.projectmanagement.exceptions.ResourceNotFoundException;
import com.justine.projectmanagement.model.Employee;
import com.justine.projectmanagement.repository.EmployeeRepository;
import com.justine.projectmanagement.specification.EmployeeSpecifications;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    SecurityUtil securityUtil;


    public Employee register(Employee employee) {
        Employee emp = employeeRepository.findByEmail(employee.getEmail());
        if (emp != null) {
            throw new EmployeeAlreadyExistsException("User with the provided email address already exists");
        }
        employee.setRole(EmployeeRole.ADMIN);
        return employeeRepository.save(employee);
    }

    public String verify(Employee employee) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        employee.getEmail(), employee.getPassword()
                )
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(employee.getEmail());
        } else {
            return "Wrong email or password";
        }
    }


    public Employee getEmployeeByEmailNoThrow(String email) {
        return employeeRepository.findByEmail(email);
    }

    public Employee getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee with the provided email address does not exist");
        }
        return employee;
    }

    public Employee add(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getEmployeesByRoleAndCompany(EmployeeRole role, String companyName) {
        return this.employeeRepository.findAll(EmployeeSpecifications.employeesByRoleAndCompany(role, companyName));
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public void deleteEmployeeByEmail(Long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        if(employee == null) {
            throw new ResourceNotFoundException("Employee with the provided id does not exist");
        }

        if(employee.getRole() == EmployeeRole.ADMIN) {
            throw new ForbiddenAction("Forbidden action, you can not delete admin employee");
        }

        employeeRepository.delete(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Transactional
    public Employee updateEmployee(Long employeeId, UpdateEmployeeDto updateEmployeeDto) {
        Employee employee = getEmployeeById(employeeId);
        if(employee == null) {
            throw new ResourceNotFoundException("Employee with the provided id does not exist");
        }

        if(employee.getRole() == EmployeeRole.ADMIN) {
            throw new ForbiddenAction("Forbidden action, you can not update admin role");
        }

        employee.setRole(updateEmployeeDto.getRole());

        return employeeRepository.save(employee);
    }
}
