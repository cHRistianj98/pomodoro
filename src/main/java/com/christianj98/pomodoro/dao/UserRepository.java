package com.christianj98.pomodoro.dao;

import com.christianj98.pomodoro.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUsername(String username);

    boolean existsByUsername(String username);
}
