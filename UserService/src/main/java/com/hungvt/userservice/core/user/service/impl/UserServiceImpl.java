package com.hungvt.userservice.core.user.service.impl;

import com.hungvt.userservice.core.user.repository.UserRoleRepository;
import com.hungvt.userservice.core.user.repository.UserUserRepository;
import com.hungvt.userservice.core.user.repository.UserUserRoleRepository;
import com.hungvt.userservice.core.user.service.UserService;
import com.hungvt.userservice.infrastructure.common.model.response.ResponseObject;
import com.hungvt.userservice.infrastructure.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserUserRepository userUserRepository;

    private final UserUserRoleRepository userUserRoleRepository;

    private final UserRoleRepository userRoleRepository;

    private final JwtUtils jwtUtils;

    @Override
    public ResponseObject getBulk(List<String> ids) {

        String id = jwtUtils.getId();
//        Optional<User> userOptional = userUserRepository.findById(id);
//        if (userOptional.isEmpty()) {
//            return ResponseObject.ofData(null,
//                    "Không tìm thấy người dùng: " + id,
//                    HttpStatus.NOT_FOUND);
//        }
//        User user = userOptional.get();

        List<Integer> roleRanks = userRoleRepository.findByUserId(id);
        if (roleRanks.isEmpty()) {
            // Cấp thấp nhất
            return ResponseObject.ofData(null,
                    "Bạn không có quyền truy cập. Vui lòng liên hệ quản trị viên.",
                    HttpStatus.FORBIDDEN);
        }
        int rankRole = Collections.max(roleRanks);


        return null;
    }

    @Override
    public ResponseObject getUser(String id) {
        return null;
    }

}
