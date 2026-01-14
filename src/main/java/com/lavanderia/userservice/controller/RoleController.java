package com.lavanderia.userservice.controller;

import com.lavanderia.userservice.dto.RoleDTO;
import com.lavanderia.userservice.entity.Role;
import com.lavanderia.userservice.service.RoleService;
import com.lavanderia.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Gestión de Roles y Permisos")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Crear un nuevo rol")
    public ResponseEntity<RoleDTO> createRole(@RequestParam Role.RoleName name) {
        RoleDTO createdRole = roleService.createRole(name);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los roles")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/users/{userId}/roles/{roleId}")
    @Operation(summary = "Asignar rol a usuario")
    public ResponseEntity<Void> assignRoleToUser(
            @PathVariable Long userId,
            @PathVariable Long roleId) {
        userService.assignRolesToUser(userId, List.of(roleId));
        return ResponseEntity.ok().build();
    }
}