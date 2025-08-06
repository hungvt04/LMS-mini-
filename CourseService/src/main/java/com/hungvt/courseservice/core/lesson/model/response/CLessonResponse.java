package com.hungvt.courseservice.core.lesson.model.response;

import com.hungvt.courseservice.entity.base.IsIdentified;

public interface CLessonResponse extends IsIdentified {

    String getCourseId();

    String getTitle();

    String getContent();

    String getVideoUrl();

    int getOrderNo();

}
