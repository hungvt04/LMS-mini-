package com.hungvt.courseservice.core.client;

import com.hungvt.courseservice.core.client.model.response.CUserResponse;
import com.hungvt.courseservice.infrastructure.model.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/v1/user-service/users/{id}")
    ResponseObject<CUserResponse> getUser(@PathVariable String id);

    @GetMapping
    ResponseObject<List<CUserResponse>> getBulkUser(List<String> ids);

}
