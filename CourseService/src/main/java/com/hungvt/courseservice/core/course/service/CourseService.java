package com.hungvt.courseservice.core.course.service;

import com.hungvt.courseservice.core.course.model.request.COGetSearchCourseRequest;
import com.hungvt.courseservice.core.course.model.request.COPostCourseRequest;
import com.hungvt.courseservice.core.course.model.request.COPutCourseRequest;
import com.hungvt.courseservice.infrastructure.model.response.ResponseObject;

public interface CourseService {

    ResponseObject<?> postCourse(COPostCourseRequest request);

    ResponseObject<?> putCourse(String id, COPutCourseRequest request);

    ResponseObject<?> deleteCourse(String id);

    ResponseObject<?> getCourse(String id);

    ResponseObject<?> getSearchCourses(COGetSearchCourseRequest request);

    ResponseObject<?> getUser(String id);

}
