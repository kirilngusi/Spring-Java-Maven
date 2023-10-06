package com.example.springbootjava.auth;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByEmail(String email);
}
