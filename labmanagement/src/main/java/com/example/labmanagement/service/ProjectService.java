package com.example.labmanagement.service;

import java.util.List;

import com.example.labmanagement.model.Project;

public interface ProjectService {

    Project createProject(Project project);

    Project updateProject(Long id, Project project);

    void deleteProject(Long id);

    Project getProjectById(Long id);

    List<Project> getAllProjects();
}
