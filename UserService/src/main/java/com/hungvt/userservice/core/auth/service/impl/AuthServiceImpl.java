package com.hungvt.userservice.core.auth.service.impl;

import com.hungvt.userservice.core.auth.model.request.AuthChangePasswordRequest;
import com.hungvt.userservice.core.auth.model.request.AuthLoginRequest;
import com.hungvt.userservice.core.auth.model.request.AuthRefreshTokenRequest;
import com.hungvt.userservice.core.auth.model.request.AuthRegisterRequest;
import com.hungvt.userservice.core.auth.model.response.AuthProfileResponse;
import com.hungvt.userservice.core.auth.repository.AuthTokenRepository;
import com.hungvt.userservice.core.auth.repository.AuthUserRepository;
import com.hungvt.userservice.core.auth.service.AuthService;
import com.hungvt.userservice.entity.Token;
import com.hungvt.userservice.entity.User;
import com.hungvt.userservice.infrastructure.common.model.response.ResponseObject;
import com.hungvt.userservice.infrastructure.constant.MappingUrl;
import com.hungvt.userservice.infrastructure.security.custom.CustomerUserDetail;
import com.hungvt.userservice.infrastructure.security.custom.CustomerUserDetailService;
import com.hungvt.userservice.infrastructure.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;

    private final AuthTokenRepository authTokenRepository;

    private final AuthenticationProvider authenticationManager;

    private final HttpServletRequest servletRequest;

    private final HttpServletResponse servletResponse;

    private final PasswordEncoder passwordEncoder;

    private final CustomerUserDetailService userDetailService;

    private final JwtUtils jwtUtils;

    private void saveRefreshToken(String refreshToken) {

        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path(MappingUrl.API_AUTH + "/refresh")
                .maxAge(Duration.ofHours(refreshToken.isEmpty() ? 0 : 4))
                .sameSite("Strict")
                .build();
        servletResponse.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @Override
    public ResponseObject register(AuthRegisterRequest request) {

        Optional<User> userOptional = authUserRepository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            return ResponseObject.ofData(null,
                    "Tên đăng nhập đã tồn tại.",
                    HttpStatus.CONFLICT);
        }

        userOptional = authUserRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            return ResponseObject.ofData(null,
                    "Gmail đã tồn tại.",
                    HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        authUserRepository.save(user);

        return ResponseObject.ofData(null,
                "Đăng ký thành công.",
                HttpStatus.CREATED);
    }

    @Override
    public ResponseObject login(AuthLoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        if (authentication != null && authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = this.processToken(request.getUsername());
            return ResponseObject.ofData(Map.of("accessToken", accessToken));
        } else {
            return ResponseObject.ofData(null,
                    "Tài khoản hoặc mật khẩu không chính xác.",
                    HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseObject refreshToken(String refreshToken, AuthRefreshTokenRequest request) {

        log.info("Refreshing token with refresh token: {}", refreshToken);
        log.info("Access token: {}", request.getAccessToken());

        List<Token> tokens = authTokenRepository.findByAccessTokenAndRefreshTokenAndIsDeleted(
                request.getAccessToken(), refreshToken, false
        );
        if (tokens.isEmpty()) {
            throw new RuntimeException("Không tìm thấy token hợp lệ.");
        }
        Token tokenOld = tokens.get(0);
        tokenOld.setIsDeleted(true);
        authTokenRepository.save(tokenOld);

        String username = tokenOld.getUser().getUsername() != null ?
                tokenOld.getUser().getUsername() : tokenOld.getUser().getEmail();
        String accessTokenNew = this.processToken(username);
        return ResponseObject.ofData(Map.of("accessToken", accessTokenNew));
    }

    @Override
    public ResponseObject logout() {

        String accessToken = jwtUtils.getTokenFromRequest(servletRequest);
        List<Token> tokens = authTokenRepository.findByAccessToken(accessToken);
        if (tokens.isEmpty()) {
            throw new RuntimeException("Không tìm thấy token hợp lệ.");
        }
        Token token = tokens.get(0);
        token.setIsDeleted(true);
        authTokenRepository.save(token);
        this.saveRefreshToken("");
        return ResponseObject.ofData(null, "Đăng xuất thành công.", HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseObject getProfile() {

        String accessToken = jwtUtils.getTokenFromRequest(servletRequest);
        String id = jwtUtils.getIdFromToken(accessToken);
        List<AuthProfileResponse> profileResponses = authUserRepository.findProfileById(id);
        if (profileResponses.isEmpty()) {
            return ResponseObject.ofData(null,
                    "Không tìm thấy thông tin người dùng.",
                    HttpStatus.NOT_FOUND);
        }
        return ResponseObject.ofData(profileResponses.get(0),
                "Lấy thông tin người dùng thành công.");
    }

    @Override
    public ResponseObject changePassword(AuthChangePasswordRequest request) {

        String accessToken = jwtUtils.getTokenFromRequest(servletRequest);
        String id = jwtUtils.getIdFromToken(accessToken);
        Optional<User> userOptional = authUserRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseObject.ofData(null,
                    "Không tìm thấy người dùng.",
                    HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return ResponseObject.ofData(null,
                    "Người dùng đăng nhập bằng tài khoản google không thể đổi mật khẩu.",
                    HttpStatus.CONFLICT);
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), request.getOldPassword())
            );

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            authUserRepository.save(user);
            return ResponseObject.ofData(null,
                    "Đổi mật khẩu thành công.");
        } catch (BadCredentialsException e) {
            return ResponseObject.ofData(null,
                    "Lỗi khi xác thực mật khẩu cũ.",
                    HttpStatus.UNAUTHORIZED);
        }
    }

    private String processToken(String username) {

        CustomerUserDetail userDetail = userDetailService.loadUserByUsername(username);

        String accessToken = jwtUtils.generateAccessToken(userDetail, true);
        String refreshToken = jwtUtils.generateAccessToken(userDetail, false);
        Optional<User> userOptional = authUserRepository.findById(userDetail.getId());
        Long expired = jwtUtils.getExpiredRefreshToken(refreshToken);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Token token = new Token(accessToken, refreshToken, userOptional.get(), expired);
        authTokenRepository.save(token);
        this.saveRefreshToken(refreshToken);
        return accessToken;
    }

}
