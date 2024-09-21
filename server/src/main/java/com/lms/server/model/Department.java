package com.lms.server.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long department_id;
    @Column(name = "department_name")
    private String department_name;
    @Column(name = "head_of_department")
    private String head_of_department;
    @Column(name = "department_description")
    private String department_description;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    public Department(String department_name, String head_of_department, String department_description) {
        this.department_name = department_name;
        this.head_of_department = head_of_department;
        this.department_description = department_description;
    }

    public Department() {
    }

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdated() {
        this.updated_at = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getDepartmentId() {
        return department_id;
    }

    // public void setDepartment_id(Long department_id) {
    // this.department_id = department_id;
    // }

    public String getDepartmentName() {
        return department_name;
    }

    public void setDepartmentName(String _department_name) {
        if (!(_department_name == null))
            this.department_name = _department_name.trim();
    }

    public String getHeadOfDepartment() {
        return head_of_department;
    }

    public void setHeadOfDepartment(String _head_of_department) {
        if (!(_head_of_department == null))
            this.head_of_department = _head_of_department.trim();
    }

    public String getDepartmentDescription() {
        return department_description;
    }

    public void setDepartmentDescription(String _department_description) {
        if (!(_department_description == null))
            this.department_description = _department_description.trim();
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public LocalDateTime getUpdatedAt() {
        return updated_at;
    }

}
