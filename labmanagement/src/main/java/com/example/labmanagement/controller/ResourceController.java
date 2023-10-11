package com.example.labmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.labmanagement.model.Resource;
import com.example.labmanagement.service.ResourceService;

import java.util.List;

@Controller
@RequestMapping("/resources")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @GetMapping("/list")
    public String listResources(Model model) {
        List<Resource> resources = resourceService.getAllResources();
        model.addAttribute("resources", resources);
        return "list-resources";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("resource", new Resource());
        return "add-resource";
    }

    @PostMapping("/add")
    public String addMember(@ModelAttribute Resource resource) {
        resourceService.saveResource(resource);
        return "redirect:/resources/list";
    }

    @GetMapping("/book/{id}")
    public String bookResource(@PathVariable Long id) {
        resourceService.bookResource(id);
        return "redirect:/resources/list";
    }

    @GetMapping("/unbook/{id}")
    public String unBookResource(@PathVariable Long id) {
        resourceService.unBookResource(id);
        return "redirect:/resources/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return "redirect:/resources/list";
    }
}
