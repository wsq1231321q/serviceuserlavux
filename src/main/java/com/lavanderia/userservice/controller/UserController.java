package com.lavanderia.userservice.controller;

import com.lavanderia.userservice.dto.UserCreateDTO;
import com.lavanderia.userservice.dto.UserResponseDTO;
import com.lavanderia.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Gestión de Usuarios (Empleados)")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario")
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestHeader("X-Tenant-ID") Long tenantId,
            @Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO createdUser = userService.createUser(tenantId, userCreateDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/by-tenant/{tenantId}")
    @Operation(summary = "Obtener usuarios por tenant")
    public ResponseEntity<List<UserResponseDTO>> getUsersByTenant(@PathVariable Long tenantId) {
        List<UserResponseDTO> users = userService.getUsersByTenantId(tenantId);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activar usuario")
    public ResponseEntity<UserResponseDTO> activateUser(@PathVariable Long id) {
        UserResponseDTO user = userService.activateUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Desactivar usuario")
    public ResponseEntity<UserResponseDTO> deactivateUser(@PathVariable Long id) {
        UserResponseDTO user = userService.deactivateUser(id);
        return ResponseEntity.ok(user);
    }
}