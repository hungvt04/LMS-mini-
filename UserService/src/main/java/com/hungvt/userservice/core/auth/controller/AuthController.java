package com.hungvt.userservice.core.auth.controller;

import com.hungvt.userservice.core.auth.model.request.AuthChangePasswordRequest;
import com.hungvt.userservice.core.auth.model.request.AuthLoginRequest;
import com.hungvt.userservice.core.auth.model.request.AuthRefreshTokenRequest;
import com.hungvt.userservice.core.auth.model.request.AuthRegisterRequest;
import com.hungvt.userservice.core.auth.service.AuthService;
import com.hungvt.userservice.infrastructure.constant.MappingUrl;
import com.hungvt.userservice.infrastructure.utils.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(MappingUrl.API_AUTH)
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRegisterRequest request) {
        return Helper.createResponseEntity(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest request) {
        return Helper.createResponseEntity(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue("refresh_token") String refreshToken,
                                          @RequestBody AuthRefreshTokenRequest request) {
        log.info("Refreshing token with refresh token: {}", refreshToken);
        return Helper.createResponseEntity(authService.refreshToken(refreshToken, request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return Helper.createResponseEntity(authService.logout());
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        return Helper.createResponseEntity(authService.getProfile());
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody AuthChangePasswordRequest request) {
        return Helper.createResponseEntity(authService.changePassword(request));
    }

}
