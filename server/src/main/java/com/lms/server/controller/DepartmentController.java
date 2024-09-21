package com.lms.server.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.server.model.Department;
import com.lms.server.repository.DepartmentRepository;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @GetMapping("/all")
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @PostMapping("/add")
    public Department addDepartment(@RequestBody Department department) {
        return departmentRepository.save(department);
    }

    @PostMapping("/adds")
    public List<Department> addMultiDepartment(@RequestBody List<Department> departments) {
        return departmentRepository.saveAll(departments);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<String> getDepartmentById(@PathVariable("id") Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            return ResponseEntity.ok().body(department.toString());
        } else {
            return new ResponseEntity<>("Department With Given Id::" + id + " Dose Not Exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Department> editDepartmentById(@PathVariable("id") Long id,
            @RequestBody Department department) {
        Optional<Department> departmentData = departmentRepository.findById(id);
        if (departmentData.isPresent()) {
            Department _department = departmentData.get();
            _department.setDepartmentName(department.getDepartmentName());
            _department.setHeadOfDepartment(department.getHeadOfDepartment());
            _department.setDepartmentDescription(department.getDepartmentDescription());
            // logger.info(department.getDepartmentName());
            return ResponseEntity.ok().body(departmentRepository.save(_department));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}