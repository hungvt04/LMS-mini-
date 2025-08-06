package com.hungvt.userservice.core.user.controller;

import com.hungvt.userservice.core.user.service.UserService;
import com.hungvt.userservice.infrastructure.constant.MappingUrl;
import com.hungvt.userservice.infrastructure.utils.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(MappingUrl.API_USER)
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        return Helper.createResponseEntity(userService.getUser(id));
    }

    @GetMapping
    public ResponseEntity<?> getBulk(List<String> ids) {
        return Helper.createResponseEntity(userService.getBulk(ids));
    }

}
