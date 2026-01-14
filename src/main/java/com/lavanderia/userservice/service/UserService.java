package com.lavanderia.userservice.service;

import com.lavanderia.userservice.dto.UserCreateDTO;
import com.lavanderia.userservice.dto.UserResponseDTO;
import com.lavanderia.userservice.dto.RoleDTO;
import com.lavanderia.userservice.entity.*;
import com.lavanderia.userservice.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Transactional
    public UserResponseDTO createUser(Long tenantId, UserCreateDTO userCreateDTO) {
        // Verificar que el tenant existe
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new EntityNotFoundException("Tenant no encontrado con id: " + tenantId));

        // Verificar que el username no esté duplicado en el tenant
        if (userRepository.existsByUsernameAndTenantId(userCreateDTO.getUsername(), tenantId)) {
            throw new IllegalArgumentException("El nombre de usuario ya existe en este tenant");
        }

        // Crear usuario
        User user = new User();
        user.setTenant(tenant);
        user.setUsername(userCreateDTO.getUsername());
        user.setPasswordHash(hashPassword(userCreateDTO.getPassword())); // En producción usar BCrypt
        user.setFullName(userCreateDTO.getFullName());
        user.setIsActive(true);

        User savedUser = userRepository.save(user);

        // Asignar roles si se especifican
        if (userCreateDTO.getRoleIds() != null && !userCreateDTO.getRoleIds().isEmpty()) {
            assignRolesToUser(savedUser.getId(), userCreateDTO.getRoleIds());
        }

        return getUserWithRoles(savedUser.getId());
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTOWithRoles)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
        return getUserWithRoles(id);
    }

    public List<UserResponseDTO> getUsersByTenantId(Long tenantId) {
        return userRepository.findByTenantId(tenantId).stream()
                .map(this::convertToDTOWithRoles)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDTO activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
        user.setIsActive(true);
        userRepository.save(user);
        return getUserWithRoles(id);
    }

    @Transactional
    public UserResponseDTO deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
        user.setIsActive(false);
        userRepository.save(user);
        return getUserWithRoles(id);
    }

    @Transactional
    public void assignRolesToUser(Long userId, List<Long> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        for (Long roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + roleId));

            // Verificar si ya tiene el rol asignado
            if (!userRoleRepository.existsByUserIdAndRoleId(userId, roleId)) {
                UserRole userRole = new UserRole();
                userRole.setId(new UserRoleId(userId, roleId));
                userRole.setUser(user);
                userRole.setRole(role);
                userRoleRepository.save(userRole);
            }
        }
    }

    private UserResponseDTO getUserWithRoles(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        List<RoleDTO> roles = user.getRoles().stream()
                .map(userRole -> new RoleDTO(userRole.getRole().getId(), userRole.getRole().getName()))
                .collect(Collectors.toList());

        return new UserResponseDTO(
                user.getId(),
                user.getTenant().getId(),
                user.getUsername(),
                user.getFullName(),
                user.getIsActive(),
                user.getCreatedAt(),
                roles
        );
    }

    private UserResponseDTO convertToDTOWithRoles(User user) {
        List<RoleDTO> roles = user.getRoles().stream()
                .map(userRole -> new RoleDTO(userRole.getRole().getId(), userRole.getRole().getName()))
                .collect(Collectors.toList());

        return new UserResponseDTO(
                user.getId(),
                user.getTenant().getId(),
                user.getUsername(),
                user.getFullName(),
                user.getIsActive(),
                user.getCreatedAt(),
                roles
        );
    }

    private String hashPassword(String password) {
        // En producción, usar BCryptPasswordEncoder
        return password; // Esto es solo para desarrollo
    }
}