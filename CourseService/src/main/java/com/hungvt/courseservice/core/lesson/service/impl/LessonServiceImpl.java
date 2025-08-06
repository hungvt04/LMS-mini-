package com.hungvt.courseservice.core.lesson.service.impl;

import com.hungvt.courseservice.core.lesson.model.request.CGetSearchCategoriesRequest;
import com.hungvt.courseservice.core.lesson.model.request.CPutLessonRequest;
import com.hungvt.courseservice.core.lesson.model.request.LPostLessonRequest;
import com.hungvt.courseservice.core.lesson.model.response.CLessonResponse;
import com.hungvt.courseservice.core.lesson.repository.LCourseRepository;
import com.hungvt.courseservice.core.lesson.repository.LLessonRepository;
import com.hungvt.courseservice.core.lesson.service.LessonService;
import com.hungvt.courseservice.entity.Course;
import com.hungvt.courseservice.entity.Lesson;
import com.hungvt.courseservice.infrastructure.model.response.PageableObject;
import com.hungvt.courseservice.infrastructure.model.response.ResponseObject;
import com.hungvt.courseservice.infrastructure.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LLessonRepository lessonRepository;

    private final LCourseRepository courseRepository;

    @Override
    public ResponseObject<?> postLesson(LPostLessonRequest request) {

        Optional<Course> courseOptional = courseRepository.findById(request.getCourseId());
        if (courseOptional.isEmpty()) {
            return ResponseObject.ofData(null, "Không tìm thấy khóa học với id: " + request.getCourseId(), HttpStatus.NOT_FOUND);
        }

        Optional<Lesson> lessonOptional = lessonRepository.findByCourseIdAndOrderNo(request.getCourseId(), request.getOrderNo());
        if (lessonOptional.isPresent()) {
            return ResponseObject.ofData(null, "Khóa học đã có bài học thứ: " + request.getOrderNo(), HttpStatus.CONFLICT);
        }

        Lesson lesson = new Lesson();
        lesson.setCourseId(request.getCourseId());
        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setOrderNo(request.getOrderNo());
        lessonRepository.save(lesson);
        return ResponseObject.ofData(null, "Thêm bài học thành công.");
    }

    @Override
    public ResponseObject<?> putLesson(String id, CPutLessonRequest request) {

        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        if (lessonOptional.isEmpty()) {
            return ResponseObject.ofData(null, "Không tìm thấy thể loại với ID: " + id, HttpStatus.NOT_FOUND);
        }
        Lesson lesson = lessonOptional.get();
        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setOrderNo(request.getOrderNo());
        lessonRepository.save(lesson);
        return ResponseObject.ofData(null, "Cập nhập bài học thành công.");
    }

    @Override
    public ResponseObject<?> deleteLesson(String id) {

        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        if (lessonOptional.isEmpty()) {
            return ResponseObject.ofData(null, "Không tìm thấy bài học với ID: " + id, HttpStatus.NOT_FOUND);
        }
        Lesson lesson = lessonOptional.get();
        lesson.setIsDeleted(!lesson.getIsDeleted());
        lessonRepository.save(lesson);
        return ResponseObject.ofData(null, "Cập nhập trạng thái thể loại thành công.");
    }

    @Override
    public ResponseObject<?> getLesson(String id) {

        List<CLessonResponse> categoryById = lessonRepository.getLessonById(id);
        if (categoryById.isEmpty()) {
            return ResponseObject.ofData(null, "Không tìm thấy thể loại với ID: " + id, HttpStatus.NOT_FOUND);
        }
        return ResponseObject.ofData(categoryById.get(0));
    }

    @Override
    public ResponseObject<?> getSearchLessons(CGetSearchCategoriesRequest request) {

        Pageable pageable = Helper.createPageable(request);
        return ResponseObject.ofData(
                PageableObject.of(
                        lessonRepository.getSearchLessons(pageable, request)
                ), "Lấy danh sách thành công.");
    }

}
