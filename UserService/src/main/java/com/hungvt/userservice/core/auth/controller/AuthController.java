package com.hungvt.userservice.core.auth.controller;

import com.hungvt.userservice.core.auth.model.request.AuthChangePasswordRequest;
import com.hungvt.userservice.core.auth.model.request.AuthLoginRequest;
import com.hungvt.userservice.core.auth.service.AuthService;
import com.hungvt.userservice.infrastructure.constant.MappingUrl;
import com.hungvt.userservice.infrastructure.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(MappingUrl.API_AUTH)
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest request) {
        return Helper.createResponseEntity(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue("refresh_token") String refreshToken) {
        return Helper.createResponseEntity(authService.refreshToken(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> login() {
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
