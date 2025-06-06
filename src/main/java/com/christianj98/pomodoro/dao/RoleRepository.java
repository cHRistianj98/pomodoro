package com.christianj98.pomodoro.dao;

import com.christianj98.pomodoro.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(String name);
}
