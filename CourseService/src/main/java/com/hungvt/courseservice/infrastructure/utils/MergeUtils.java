package com.hungvt.courseservice.infrastructure.utils;

import com.hungvt.courseservice.core.client.model.response.CUserResponse;
import com.hungvt.courseservice.core.course.model.response.COCourseDetailResponse;
import com.hungvt.courseservice.core.course.model.response.COCourseResponse;

import java.util.List;

public class MergeUtils {

    public static COCourseDetailResponse merge(COCourseResponse courseResponse, List<CUserResponse> users) {

        COCourseDetailResponse response = new COCourseDetailResponse();
        response.setId(courseResponse.getId());
        response.setCourseTitle(courseResponse.getCourseTitle());
        response.setPrice(courseResponse.getPrice());
        response.setDescription(courseResponse.getDescription());
        response.setIsDeleted(courseResponse.getIsDeleted());
        response.setCategoryId(courseResponse.getCategoryId());
        response.setCategoryName(courseResponse.getCategoryName());
        response.setTeacherId(courseResponse.getTeacherId());

        List<CUserResponse> usersFilter = users.stream()
                .filter(user -> user.getId().equalsIgnoreCase(courseResponse.getTeacherId()))
                .toList();
        if (!usersFilter.isEmpty()) {
            response.setTeacherName(usersFilter.get(0).getUsername());
        }
        return response;
    }

}
