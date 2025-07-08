package com.hungvt.userservice.core.auth.service;

import com.hungvt.userservice.core.auth.model.request.AuthChangePasswordRequest;
import com.hungvt.userservice.core.auth.model.request.AuthLoginRequest;
import com.hungvt.userservice.core.auth.model.request.AuthRefreshTokenRequest;
import com.hungvt.userservice.core.auth.model.request.AuthRegisterRequest;
import com.hungvt.userservice.infrastructure.common.model.response.ResponseObject;

public interface AuthService {

    ResponseObject register(AuthRegisterRequest request);

    ResponseObject login(AuthLoginRequest request);

    ResponseObject refreshToken(String refreshToken, AuthRefreshTokenRequest request);

    ResponseObject logout();

    ResponseObject getProfile();

    ResponseObject changePassword(AuthChangePasswordRequest request);

}
