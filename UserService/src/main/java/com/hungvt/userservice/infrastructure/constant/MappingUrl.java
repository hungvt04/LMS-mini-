package com.hungvt.userservice.infrastructure.constant;

public class MappingUrl {

    public static final String API_VERSION = "/api/v1";

//    public static final String API_VERSION = "";

    public static final String API_VERSION_USER_SERVICE = API_VERSION + "/user-service";

    public static final String API_USER = API_VERSION_USER_SERVICE + "/users";

    public static final String API_USER_ROLE = API_VERSION_USER_SERVICE + "/users-roles";

    public static final String API_ROLE = API_VERSION_USER_SERVICE + "/roles";

    public static final String API_PERMISSION = API_VERSION_USER_SERVICE + "/permissions";

    public static final String API_ROLE_PERMISSION = API_VERSION_USER_SERVICE + "/roles-permissions";

    public static final String API_AUTH = API_VERSION_USER_SERVICE + "/auth";

    public static final String API_URL_EXCEPTION = API_VERSION + "/exceptions";

}
