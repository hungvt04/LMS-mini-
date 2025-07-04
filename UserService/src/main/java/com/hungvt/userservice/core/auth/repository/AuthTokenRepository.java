package com.hungvt.userservice.core.auth.repository;

import com.hungvt.userservice.entity.Token;
import com.hungvt.userservice.repository.TokenRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthTokenRepository extends TokenRepository {

    List<Token> findByAccessToken(String accessToken);

    List<Token> findByAccessTokenAndRefreshTokenAndIsDeleted(String accessToken, String refreshToken, Boolean isDeleted);

}
