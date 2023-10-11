package com.example.labmanagement.controller;

import com.example.labmanagement.model.Member;
import com.example.labmanagement.model.Publication;
import com.example.labmanagement.service.PublicationService;
import com.example.labmanagement.service.ProjectService;
import com.example.labmanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/publications")
public class PublicationController {

    private final PublicationService publicationService;
    private final ProjectService projectService;
    private final UserService memberService;

    public PublicationController(
            PublicationService publicationService,
            ProjectService projectService,
            UserService memberService
    ) {
        this.publicationService = publicationService;
        this.projectService = projectService;
        this.memberService = memberService;
    }

    @GetMapping("/list")
    public String listPublications(Model model) {
        List<Publication> publications = publicationService.getAllPublications();
        Collections.reverse(publications);
        model.addAttribute("publications", publications);
        return "list-publications"; // Create a Thymeleaf template for listing publications
    }

    
    @GetMapping("/{id}")
    public String viewPublication(@PathVariable Long id, Model model) {
        Publication publication = publicationService.getPublicationById(id);
        model.addAttribute("publication", publication);
        return "publication-details"; // Create a Thymeleaf template for publication details
    }

    @GetMapping("/edit/{id}")
    public String editPublicationForm(@PathVariable Long id, Model model) {
        Publication publication = publicationService.getPublicationById(id);
        model.addAttribute("publication", publication);
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("members", memberService.findAllUsers());
        return "edit-publication"; // Create a Thymeleaf template for editing publication
    }

    @PostMapping("/edit/{id}")
    public String updatePublication(@PathVariable Long id, @ModelAttribute Publication updatedPublication) {
        // Retrieve the existing publication by ID
        Publication existingPublication = publicationService.getPublicationById(id);

        // Update the properties of the existing publication with the edited values
        existingPublication.setTitle(updatedPublication.getTitle());
        existingPublication.setAuthors(updatedPublication.getAuthors());
        existingPublication.setPublicationDate(updatedPublication.getPublicationDate());
        existingPublication.setAdditionalAuthors(updatedPublication.getAdditionalAuthors());
        existingPublication.setProject(updatedPublication.getProject());

        // Save the updated publication
        publicationService.savePublication(existingPublication);

        return "redirect:/publications/" + id; // Redirect to view after editing
    }

    @GetMapping("/delete/{id}")
    public String deletePublication(@PathVariable Long id) {
        publicationService.deletePublicationById(id);
        return "redirect:/publications/list"; // Redirect to publications list after deletion
    }

    @GetMapping("/add")
    public String addPublicationForm(Model model) {
        model.addAttribute("newPublication", new Publication());
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("members", memberService.findAllUsers());
        return "add-publication"; // Create a Thymeleaf template for adding a new publication
    }

    @PostMapping("/add")
    public String addPublication(
        @ModelAttribute Publication publication,
        @RequestParam List<Long> selectedMembers,
        @RequestParam("additionalAuthors") String additionalAuthors) {
        // Retrieve selected members and associate them with the publication
        List<Member> authors = memberService.getMembersByIds(selectedMembers);

        // Parse the additional authors and add them to the publication
        List<String> additionalAuthorsList = Arrays.asList(additionalAuthors.split(","));
        publication.setAuthors(authors);
        publication.setAdditionalAuthors(additionalAuthorsList);
        publicationService.savePublication(publication);
        return "redirect:/publications/list"; // Redirect to publications list after adding
    } 
}

