package com.hungvt.courseservice.core.lesson.controller;

import com.hungvt.courseservice.core.lesson.model.request.CGetSearchCategoriesRequest;
import com.hungvt.courseservice.core.lesson.model.request.CPutLessonRequest;
import com.hungvt.courseservice.core.lesson.model.request.LPostLessonRequest;
import com.hungvt.courseservice.core.lesson.service.LessonService;
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
@RequestMapping(MappingUrl.API_LESSON)
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public ResponseEntity<?> postLesson(@RequestBody LPostLessonRequest request) {
        return Helper.createResponseEntity(lessonService.postLesson(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putLesson(@PathVariable String id, @RequestBody CPutLessonRequest request) {
        return Helper.createResponseEntity(lessonService.putLesson(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable String id) {
        return Helper.createResponseEntity(lessonService.deleteLesson(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLesson(@PathVariable String id) {
        return Helper.createResponseEntity(lessonService.getLesson(id));
    }

    @GetMapping
    public ResponseEntity<?> getSearchLessons(CGetSearchCategoriesRequest request) {
        return Helper.createResponseEntity(lessonService.getSearchLessons(request));
    }

}
