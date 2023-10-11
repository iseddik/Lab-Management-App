package com.example.labmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.labmanagement.model.Role;
import com.example.labmanagement.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    // Additional methods for role-related operations if needed
}
