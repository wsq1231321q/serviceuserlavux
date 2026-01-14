package com.lavanderia.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantResponseDTO {

    private Long id;
    private String name;
    private String domain;
    private Boolean isActive;
    private LocalDateTime createdAt;
}