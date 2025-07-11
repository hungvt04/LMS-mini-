package com.hungvt.courseservice.core.course.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class COPutCourseRequest {

    private String courseTitle;

    private String description;

    private String categoryId;

    private String teacherId;

    private Float price;

}
