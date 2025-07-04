package com.hungvt.userservice.infrastructure.security.custom;

import com.hungvt.userservice.entity.User;
import com.hungvt.userservice.repository.RolePermissionRepository;
import com.hungvt.userservice.repository.UserRepository;
import com.hungvt.userservice.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomerUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final RolePermissionRepository rolePermissionRepository;

    @Override
    public CustomerUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByEmail(username);
            if (userOptional.isEmpty()) {
                log.error("Not found user by email: {}", username);
                throw new UsernameNotFoundException("Not found user by email and username: " + username);
            }
        }

        User user = userOptional.get();
        CustomerUserDetail userDetail = new CustomerUserDetail();
        userDetail.setId(user.getId());
        userDetail.setUsername(user.getUsername());
        userDetail.setPassword(user.getPassword());
        userDetail.setEmail(user.getEmail());
        userDetail.setRoles(userRoleRepository.findByUser(username));
        userDetail.setPermissions(rolePermissionRepository.findByUser(username));
        return userDetail;
    }
}
