package com.justine.projectmanagement.service;

import com.justine.projectmanagement.authentication.SecurityUtil;
import com.justine.projectmanagement.model.Company;
import com.justine.projectmanagement.model.Employee;
import com.justine.projectmanagement.repository.CompanyRepository;
import com.justine.projectmanagement.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Company save(Company company) {
        Company savedCompany = companyRepository.save(company);
        Employee employee = this.employeeRepository.findByEmail(securityUtil.getCurrentUser().getUsername());
        employee.setCompany(savedCompany);
        this.employeeRepository.save(employee);
        return savedCompany;
    }
}
