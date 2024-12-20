package com.justine.projectmanagement.service;


import com.justine.projectmanagement.authentication.SecurityUtil;
import com.justine.projectmanagement.enums.EmployeeRole;
import com.justine.projectmanagement.model.Employee;
import com.justine.projectmanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
        // TODO: check if the email address already exists
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
            return "fail";
        }
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public Employee add(Employee employee) {
        return employeeRepository.save(employee);
    }
}
