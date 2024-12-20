package com.justine.projectmanagement.controller;


import com.justine.projectmanagement.model.Company;
import com.justine.projectmanagement.service.CompanyService;
import com.justine.projectmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/create")
    public Company create(@RequestBody Company company) {
        return this.companyService.save(company);
    }
}
