package com.justine.projectmanagement.repository;

import com.justine.projectmanagement.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
