package com.lavanderia.userservice.controller;

import com.lavanderia.userservice.dto.TenantRequestDTO;
import com.lavanderia.userservice.dto.TenantResponseDTO;
import com.lavanderia.userservice.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
@Tag(name = "Tenants", description = "Gestión de Tenants (Lavanderías)")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @PostMapping
    @Operation(summary = "Crear un nuevo tenant")
    public ResponseEntity<TenantResponseDTO> createTenant(@Valid @RequestBody TenantRequestDTO tenantRequest) {
        TenantResponseDTO createdTenant = tenantService.createTenant(tenantRequest);
        return new ResponseEntity<>(createdTenant, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los tenants")
    public ResponseEntity<List<TenantResponseDTO>> getAllTenants() {
        List<TenantResponseDTO> tenants = tenantService.getAllTenants();
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tenant por ID")
    public ResponseEntity<TenantResponseDTO> getTenantById(@PathVariable Long id) {
        TenantResponseDTO tenant = tenantService.getTenantById(id);
        return ResponseEntity.ok(tenant);
    }
}