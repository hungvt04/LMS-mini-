package com.hungvt.userservice.core.auth.model.response;

import com.hungvt.userservice.entity.base.IsIdentified;

public interface AuthProfileResponse extends IsIdentified {

    String getUsername();

    String getEmail();

    String getFullName();

    String getAvatar();

    Long getCreatedAt();

    Long getUpdatedAt();

}
