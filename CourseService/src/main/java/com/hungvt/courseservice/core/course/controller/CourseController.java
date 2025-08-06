package com.hungvt.courseservice.core.course.controller;

import com.hungvt.courseservice.core.course.model.request.COGetSearchCourseRequest;
import com.hungvt.courseservice.core.course.model.request.COPostCourseRequest;
import com.hungvt.courseservice.core.course.model.request.COPutCourseRequest;
import com.hungvt.courseservice.core.course.service.CourseService;
import com.hungvt.courseservice.infrastructure.constant.MappingUrl;
import com.hungvt.courseservice.infrastructure.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MappingUrl.API_COURSE)
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<?> postCategory(@RequestBody COPostCourseRequest request) {
        return Helper.createResponseEntity(courseService.postCourse(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCategory(@PathVariable String id, @RequestBody COPutCourseRequest request) {
        return Helper.createResponseEntity(courseService.putCourse(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        return Helper.createResponseEntity(courseService.deleteCourse(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable String id) {
        return Helper.createResponseEntity(courseService.getCourse(id));
    }

    @GetMapping
    public ResponseEntity<?> getSearchCategories(COGetSearchCourseRequest request) {
        return Helper.createResponseEntity(courseService.getSearchCourses(request));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        return Helper.createResponseEntity(courseService.getUser(id));
    }

}
