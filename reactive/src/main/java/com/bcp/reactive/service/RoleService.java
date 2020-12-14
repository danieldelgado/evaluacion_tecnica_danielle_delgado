package com.bcp.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcp.reactive.entity.Role;
import com.bcp.reactive.repository.RoleRepository;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByRole(String roleStr) {
        return roleRepository.findByRole(roleStr);
    }

    public Role saveRole(String role) {
        Role roleObj = new Role(role);
        roleRepository.save(roleObj);
        return roleObj;
    }
}