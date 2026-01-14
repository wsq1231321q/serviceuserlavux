package com.lavanderia.userservice.repository;

import com.lavanderia.userservice.entity.UserRole;
import com.lavanderia.userservice.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);
}