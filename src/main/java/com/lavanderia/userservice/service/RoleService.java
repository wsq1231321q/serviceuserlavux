package com.lavanderia.userservice.service;

import com.lavanderia.userservice.dto.RoleDTO;
import com.lavanderia.userservice.entity.Role;
import com.lavanderia.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleDTO createRole(Role.RoleName roleName) {
        if (roleRepository.findByName(roleName).isPresent()) {
            throw new IllegalArgumentException("El rol ya existe");
        }

        Role role = new Role();
        role.setName(roleName);

        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RoleDTO convertToDTO(Role role) {
        return new RoleDTO(role.getId(), role.getName());
    }
}