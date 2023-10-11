package com.example.labmanagement.controller;

import com.example.labmanagement.service.UserService;
import com.example.labmanagement.model.Member;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.security.core.context.SecurityContextHolder;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        Member user = new Member();
        model.addAttribute("user", user);
        return "register";
    }
    

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") Member user,
                               BindingResult result,
                               Model model){
        Member existingUser = userService.findUserByEmail(user.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "/register";
        }

        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/dashboard")
    public String users(Model model){
        List<Member> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "dashboard";
    }

    @GetMapping({"/" , "/login" })
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        // Log the user out by clearing the security context
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        // Redirect to the login page or a custom logout page
        return "redirect:/login"; // You can replace "/login" with your desired logout page
    }

}
