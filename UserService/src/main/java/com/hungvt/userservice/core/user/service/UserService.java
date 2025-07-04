package com.hungvt.userservice.core.user.service;

import com.hungvt.userservice.infrastructure.common.model.response.ResponseObject;

import java.util.List;

public interface UserService {

    ResponseObject getBulk(List<String> ids);

    ResponseObject getUser(String id);

}
