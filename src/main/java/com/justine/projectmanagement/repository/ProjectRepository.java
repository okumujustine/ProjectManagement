package com.justine.projectmanagement.repository;

import com.justine.projectmanagement.model.Project;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(name = "Project.findByCompanyName")
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Project> findByCompanyName(@Param("companyName") String companyName);
}
