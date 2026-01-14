package com.lavanderia.userservice.repository;

import com.lavanderia.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByTenantId(Long tenantId);
    Optional<User> findByUsernameAndTenantId(String username, Long tenantId);
    boolean existsByUsernameAndTenantId(String username, Long tenantId);
}