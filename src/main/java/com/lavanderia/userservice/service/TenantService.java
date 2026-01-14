package com.lavanderia.userservice.service;

import com.lavanderia.userservice.dto.TenantRequestDTO;
import com.lavanderia.userservice.dto.TenantResponseDTO;
import com.lavanderia.userservice.entity.Tenant;
import com.lavanderia.userservice.repository.TenantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Transactional
    public TenantResponseDTO createTenant(TenantRequestDTO tenantRequest) {
        if (tenantRepository.existsByDomain(tenantRequest.getDomain())) {
            throw new IllegalArgumentException("El dominio ya está registrado");
        }

        Tenant tenant = new Tenant();
        tenant.setName(tenantRequest.getName());
        tenant.setDomain(tenantRequest.getDomain());
        tenant.setIsActive(true);

        Tenant savedTenant = tenantRepository.save(tenant);
        return convertToDTO(savedTenant);
    }

    public List<TenantResponseDTO> getAllTenants() {
        return tenantRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TenantResponseDTO getTenantById(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tenant no encontrado con id: " + id));
        return convertToDTO(tenant);
    }

    private TenantResponseDTO convertToDTO(Tenant tenant) {
        return new TenantResponseDTO(
                tenant.getId(),
                tenant.getName(),
                tenant.getDomain(),
                tenant.getIsActive(),
                tenant.getCreatedAt()
        );
    }
}