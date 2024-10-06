package com.microservice.auth_service.Repository;

import com.microservice.auth_service.Entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential,Long> {

    Optional<UserCredential> findByUsername(String username);
}
