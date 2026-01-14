package com.lavanderia.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private Long tenantId;
    private String username;
    private String fullName;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private List<RoleDTO> roles;
}