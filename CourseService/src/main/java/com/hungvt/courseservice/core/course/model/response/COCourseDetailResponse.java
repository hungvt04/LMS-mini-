package com.hungvt.courseservice.core.course.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class COCourseDetailResponse {

    private String id;

    private String courseTitle;

    private String description;

    private Float price;

    private String categoryId;

    private String categoryName;

    private String teacherId;

    private String teacherName;

    private Boolean isDeleted;

}
