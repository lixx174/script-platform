package com.j.domain.repository;

import com.j.domain.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Jinx
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByAccessToken(String accessToken);

    @Query("select t from Token t where t.user.id = :userId")
    Optional<Token> findByUserId(Long userId);
}
