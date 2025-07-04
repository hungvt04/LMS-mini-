package com.hungvt.userservice.repository;

import com.hungvt.userservice.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

    @Query(value = """
            SELECT t FROM Token t
            WHERE t.accessToken = :accessToken
            """)
    List<Token> findTokensByAccessToken(String accessToken);

    @Query(value = """
            SELECT t FROM Token t
            WHERE t.user.id = :userId
            """)
    List<Token> findTokensByUser(String userId);
}
