package com.example.labmanagement.service;

import com.example.labmanagement.repository.MemberRepository;
import com.example.labmanagement.repository.RoleRepository;
import com.example.labmanagement.model.Member;
import com.example.labmanagement.model.Role;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
    
    private MemberRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    
    public UserService(MemberRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void saveUser(Member user) {
     
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    public Member findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Member> findAllUsers() {
        List<Member> users = userRepository.findAll();
        return users;
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    
    @Transactional(readOnly = true)
    public Member getMemberById(Long id) {
        Optional<Member> optionalMember = userRepository.findById(id);
        return optionalMember.orElse(null);
    }

    
    @Transactional(readOnly = true)
    public List<Member> getMembersByIds(List<Long> ids) {
        return userRepository.findAllById(ids);
    }


    @Transactional
    public void updateMember(Long id, Member member) {
        Optional<Member> optionalMember = userRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member existingMember = optionalMember.get();
            // Update the properties of the existing member with the new values from the parameter 'member'
            existingMember.setFirstName(member.getFirstName());
            existingMember.setLastName(member.getLastName());
            existingMember.setEmail(member.getEmail());
            existingMember.setPassword(passwordEncoder.encode(member.getPassword()));
            existingMember.setProjects(member.getProjects());
            existingMember.setRoles(member.getRoles());
            userRepository.save(existingMember);
        }
    }

    @Transactional
    public void deleteMember(Long id) {
        userRepository.deleteById(id);
    }
}
