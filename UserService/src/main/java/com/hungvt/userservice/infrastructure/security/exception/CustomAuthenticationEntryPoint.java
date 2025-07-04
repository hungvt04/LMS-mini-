package com.hungvt.userservice.infrastructure.security.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungvt.userservice.infrastructure.common.model.response.ResponseObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
/**
 * Handle login with username and password
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        try {
            ResponseObject data = ResponseObject.ofData(null, "Bạn chưa đăng nhập. Vui lòng đăng nhập để tiếp tục.");
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
