package com.hungvt.userservice.infrastructure.utils;

import com.hungvt.userservice.infrastructure.security.custom.CustomerUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${jwt.expiredAccessToken}")
    private long EXPIRED_ACCESS_TOKEN;

    @Value("${jwt.expiredRefreshToken}")
    private long EXPIRED_REFRESH_TOKEN;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private Key getSigningKey() {

        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String getRole(Collection<? extends GrantedAuthority> authorities) {

        StringBuilder result = new StringBuilder();
        for (GrantedAuthority grantedAuthority : authorities) {
            result.append(grantedAuthority.getAuthority()).append(", ");
        }

        return result.substring(0, result.length() - 2);
    }

    public String generateAccessToken(CustomerUserDetail userDetails, boolean isAccessToken) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("id", userDetails.getId());
        claims.put("email", userDetails.getEmail());
        long expiedTime = EXPIRED_REFRESH_TOKEN;

        if (isAccessToken) {
            claims.put("role", userDetails.getRoles().toString());
            expiedTime = EXPIRED_ACCESS_TOKEN;
        }

        return Jwts.builder()
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + expiedTime))
                .issuedAt(new Date())
                .signWith(getKey())
                .compact();
    }

    public Long getExpiredRefreshToken(String token) {

        return this.getClaims(token)
                .getExpiration()
                .getTime();
    }

    public Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getIdFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", String.class);
    }

    public boolean isValidToken(String token) {

        try {
            String id = getIdFromToken(token);
            if (id == null || id.isEmpty()) {
                log.error("❌ Token không hợp lệ: Không tìm thấy ID trong token");
                return false;
            }
            return true;
        } catch (ExpiredJwtException e) {
            log.error("❌ Token hết hạn: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("❌ Token không được hỗ trợ: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("❌ Token sai định dạng: {}", e.getMessage());
        } catch (SecurityException e) {
            log.error("❌ Chữ ký Token không hợp lệ: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("❌ Token rỗng hoặc null: {}", e.getMessage());
        }
        return false;
    }

    public String getTokenFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
