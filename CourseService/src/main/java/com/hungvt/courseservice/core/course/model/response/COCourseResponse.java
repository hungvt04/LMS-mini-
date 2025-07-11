package com.hungvt.courseservice.core.course.model.response;

import com.hungvt.courseservice.entity.base.IsIdentified;

public interface COCourseResponse extends IsIdentified {

    String getCourseTitle();

    Float getPrice();

    String getDescription();

    Boolean getIsDeleted();

    String getCategoryId();

    String getCategoryName();

    String getTeacherId();

    Long getCreatedAt();

}
