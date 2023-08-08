package com.moraes.microservices.statefulauthapi.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moraes.microservices.statefulauthapi.api.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findTopByUsername(String username);
}
