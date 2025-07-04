package com.hungvt.userservice.infrastructure.security.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungvt.userservice.entity.Token;
import com.hungvt.userservice.entity.User;
import com.hungvt.userservice.infrastructure.common.model.response.ResponseObject;
import com.hungvt.userservice.infrastructure.utils.JwtUtils;
import com.hungvt.userservice.repository.TokenRepository;
import com.hungvt.userservice.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
/**
 * Handle login with google
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String fullName = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        User user;
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (user.getIsDeleted()) {
                log.info("User with email {} is deleted, creating a new user.", email);
                throw new RuntimeException("Tài khoản đã bị vô hiệu hóa. Vui lòng liên hệ quản trị viên để được hỗ trợ.");
            }
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                throw new RuntimeException("Email đã được liên kết với tài khoản khác. Vui lòng liên hệ quản trị viên để được hỗ trợ.");
            }

        } else {
            user = new User();
        }

        user.setEmail(email);
        user.setFullName(fullName);
        user.setAvatar(picture);
        user = userRepository.save(user);

        List<Token> tokens = tokenRepository.findTokensByUser(user.getId());
        if (!tokens.isEmpty()) {
            tokenRepository.deleteAll(tokens);
        }

        CustomerUserDetail customerUserDetail = new CustomerUserDetail();
        customerUserDetail.setEmail(user.getEmail());
        customerUserDetail.setUsername(user.getUsername());
        customerUserDetail.setPassword(user.getPassword());

        String accessToken = jwtUtils.generateAccessToken(customerUserDetail, true);
        String refreshToken = jwtUtils.generateAccessToken(customerUserDetail, false);

        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setExpired(jwtUtils.getExpiredRefreshToken(refreshToken));
        token.setUser(user);
        tokenRepository.save(token);

        try {
            Map<String, String> responseData = Map.of(
                    "accessToken", accessToken
            );
            ResponseObject data = ResponseObject.ofData(responseData);
            String json = new ObjectMapper().writeValueAsString(data);

            response.setStatus(HttpServletResponse.SC_OK);
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
