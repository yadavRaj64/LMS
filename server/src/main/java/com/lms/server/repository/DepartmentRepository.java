package com.lms.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.server.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
