package com.example.labmanagement.controller;

import com.example.labmanagement.model.Member;
import com.example.labmanagement.model.Project;
import com.example.labmanagement.service.UserService;
import com.example.labmanagement.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService memberService;

    public ProjectController(ProjectService projectService, UserService memberService) {
        this.projectService = projectService;
        this.memberService = memberService;
    }

    @GetMapping("/list")
    public String listProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        Collections.reverse(projects);
        model.addAttribute("projects", projects);
        return "list-projects"; // Thymeleaf template name
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        List<Member> members = memberService.findAllUsers(); // Get a list of all members
        model.addAttribute("project", new Project());
        model.addAttribute("members", members); // Add the list of members to the model
        return "add-project";
    }

    @PostMapping("/add")
    public String addProject(
            @ModelAttribute Project project,
            @RequestParam List<Long> selectedMembers,
            @RequestParam("additionalAuthors") String additionalAuthors) {
        // Retrieve selected members and associate them with the project
        List<Member> authors = memberService.getMembersByIds(selectedMembers);

        // Parse the additional authors and add them to the project
        List<String> additionalAuthorsList = Arrays.asList(additionalAuthors.split(","));
        project.setAuthors(authors);
        project.setAdditionalAuthors(additionalAuthorsList);

        projectService.createProject(project);
        return "redirect:/projects/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Project project = projectService.getProjectById(id);
        List<Member> members = memberService.findAllUsers(); // Get a list of all members
        model.addAttribute("project", project);
        model.addAttribute("members", members); // Add the list of members to the model
        return "edit-project";
    }

    @PostMapping("/edit/{id}")
    public String editProject(@PathVariable Long id, @ModelAttribute Project project,
            @RequestParam List<Long> selectedMembers) {
        // Retrieve selected members and associate them with the project
        List<Member> authors = memberService.getMembersByIds(selectedMembers);
        project.setAuthors(authors);

        projectService.updateProject(id, project);
        return "redirect:/projects/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects/list";
    } 
}
