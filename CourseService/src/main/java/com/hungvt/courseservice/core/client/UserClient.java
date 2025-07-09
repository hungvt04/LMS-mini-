package com.hungvt.courseservice.core.client;

import com.hungvt.courseservice.infrastructure.constant.MappingUrl;
import com.hungvt.courseservice.infrastructure.model.response.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "user-service")
@RequestMapping(MappingUrl.PREFIX_USER_CLIENT)
public interface UserClient {

    @GetMapping("/{id}")
    ResponseObject getUser(@PathVariable String id);

}
