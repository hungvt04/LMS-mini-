package com.hungvt.userservice.infrastructure.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungvt.userservice.entity.Token;
import com.hungvt.userservice.infrastructure.common.model.response.ResponseObject;
import com.hungvt.userservice.infrastructure.security.custom.CustomerUserDetail;
import com.hungvt.userservice.infrastructure.security.custom.CustomerUserDetailService;
import com.hungvt.userservice.infrastructure.utils.JwtUtils;
import com.hungvt.userservice.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final TokenRepository tokenRepository;

    private final CustomerUserDetailService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtils.getTokenFromRequest(request);
        log.info("Token from header: {}", token);
        if (token != null && !token.trim().isEmpty() && !token.equalsIgnoreCase("{{bearerToken}}")) {

            boolean isValidToken = jwtUtils.isValidToken(token);
            List<Token> tokens = tokenRepository.findTokensByAccessToken(token);

            if (!isValidToken || tokens.isEmpty()) {
                try {
                    ResponseObject data = ResponseObject.ofData(null,
                            "Token không hợp lệ hoặc đã hết hạn, vui lòng đăng nhập lại.");
                    String json = new ObjectMapper().writeValueAsString(data);

                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                    log.info("Token is invalid!!!");
                } catch (JsonProcessingException e) {
                    log.error("Không thể chuyển đổi object sang chuỗi: {}", e.getMessage());
                } catch (IOException e) {
                    log.error("Lỗi IOException: {}", e.getMessage());
                }
                return;
            }

            Claims claims = jwtUtils.getClaims(token);

            String username = claims.get("username", String.class);
            if (username == null || username.isEmpty()) {
                username = claims.get("email", String.class);
            }
            CustomerUserDetail userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);

    }
}
