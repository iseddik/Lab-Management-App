package com.example.labmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.labmanagement.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    
}
