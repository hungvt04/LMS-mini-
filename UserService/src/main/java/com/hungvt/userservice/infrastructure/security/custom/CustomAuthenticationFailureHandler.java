package com.hungvt.userservice.infrastructure.security.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungvt.userservice.infrastructure.common.model.response.ResponseObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
/**
 * Handle login with google
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        try {
            ResponseObject data = ResponseObject.ofData(null, "Đăng nhập không thành công, vui lòng thử lại.");
            String json = new ObjectMapper().writeValueAsString(data);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (JsonProcessingException e) {
            log.error("Không thể chuyển đổi object sang chuỗi: {}", e.getMessage());
        } catch (IOException e) {
            log.error("Lỗi IOException: {}", e.getMessage());
        }
    }

}
