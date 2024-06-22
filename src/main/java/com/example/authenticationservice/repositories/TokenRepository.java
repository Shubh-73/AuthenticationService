package com.example.authenticationservice.repositories;

import com.example.authenticationservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByValueAndDeleted(String value, boolean isDeleted);
    Optional<Token> findByValueAndDeletedAndExpirationGreaterThan(
            String value, boolean isDeleted, Date expiration
    );
}