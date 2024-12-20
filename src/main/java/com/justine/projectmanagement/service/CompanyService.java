package com.justine.projectmanagement.service;

import com.justine.projectmanagement.authentication.SecurityUtil;
import com.justine.projectmanagement.exceptions.CompanyAlreadyCreatedException;
import com.justine.projectmanagement.exceptions.CompanyAlreadyExistsException;
import com.justine.projectmanagement.exceptions.CompanyDoesNotExists;
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
        Employee employee = this.employeeRepository.findByEmail(securityUtil.getCurrentUser().getUsername());
        if(employee.getCompany() != null) {
            throw new CompanyAlreadyCreatedException("You already created a company " + employee.getCompany().getName());
        }
        Company savedCompany = companyRepository.save(company);
        employee.setCompany(savedCompany);
        this.employeeRepository.save(employee);
        return savedCompany;
    }

    public Company findById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new CompanyDoesNotExists("Company with id " + id + "does not exists"));
    }
}
