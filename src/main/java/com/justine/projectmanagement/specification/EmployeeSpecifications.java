package com.justine.projectmanagement.specification;

import com.justine.projectmanagement.enums.EmployeeRole;
import com.justine.projectmanagement.model.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecifications {
    public static Specification<Employee> employeesByRoleAndCompany(EmployeeRole role, String companyName) {
        return (Root<Employee> employeeRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate equalRoom = criteriaBuilder.equal(employeeRoot.get("role"), role);
            Predicate equalYear = criteriaBuilder.equal(employeeRoot.get("company").get("name"), companyName);
            return criteriaBuilder.and(equalRoom, equalYear);
        };
    }
}
