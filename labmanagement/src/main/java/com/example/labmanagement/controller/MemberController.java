package com.example.labmanagement.controller;

import com.example.labmanagement.model.Member;
import com.example.labmanagement.model.Role;
import com.example.labmanagement.service.RoleService;
import com.example.labmanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final UserService memberService;

    private final RoleService roleService;

    public MemberController(UserService memberService, RoleService roleService) {
        this.memberService = memberService;
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public String listMembers(Model model) {
        List<Member> members = memberService.findAllUsers();
        model.addAttribute("members", members); // This is for the list view
        model.addAttribute("allMembers", members); // This is for the select dropdown
        return "list-members"; // Thymeleaf template name
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("member", new Member());
        return "add-member";
    }

    @PostMapping("/add")
    public String addMember(@ModelAttribute Member member) {
        memberService.saveUser(member);
        return "redirect:/members/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Member member = memberService.getMemberById(id);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("member", member);
        model.addAttribute("roles", roles);
        return "edit-member";
    }

    @PostMapping("/edit/{id}")
    public String editMember(@PathVariable Long id, @ModelAttribute Member updatedMember, @RequestParam List<String> selectedRoles) {
        List<Role> updatedRoles = new ArrayList<>();
        for (String roleName : selectedRoles) {  
            Role role = roleService.findByName(roleName); 
            if (role != null) {    
                updatedRoles.add(role);
            }
        }
        updatedMember.setRoles(updatedRoles);
        memberService.updateMember(id, updatedMember);
        return "redirect:/members/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return "redirect:/members/list";
    }
}
