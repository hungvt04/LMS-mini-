package com.hungvt.courseservice.core.course.service.impl;

import com.hungvt.courseservice.core.client.UserClient;
import com.hungvt.courseservice.core.client.model.response.CUserResponse;
import com.hungvt.courseservice.core.course.model.request.COGetSearchCourseRequest;
import com.hungvt.courseservice.core.course.model.request.COPostCourseRequest;
import com.hungvt.courseservice.core.course.model.request.COPutCourseRequest;
import com.hungvt.courseservice.core.course.model.response.COCourseDetailResponse;
import com.hungvt.courseservice.core.course.model.response.COCourseResponse;
import com.hungvt.courseservice.core.course.repository.COCategoryRepository;
import com.hungvt.courseservice.core.course.repository.COCourseRepository;
import com.hungvt.courseservice.core.course.service.CourseService;
import com.hungvt.courseservice.entity.Course;
import com.hungvt.courseservice.infrastructure.model.response.PageableObject;
import com.hungvt.courseservice.infrastructure.model.response.ResponseObject;
import com.hungvt.courseservice.infrastructure.utils.Helper;
import com.hungvt.courseservice.infrastructure.utils.MergeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final UserClient userClient;

    private final COCourseRepository courseRepository;

    private final COCategoryRepository categoryRepository;

    @Override
    public ResponseObject<?> postCourse(COPostCourseRequest request) {

        Optional<Course> courseOptional = courseRepository.findById(request.getCategoryId());
        if (courseOptional.isEmpty()) {
            return ResponseObject.ofData(null,
                    "Không tìm thấy khóa học với ID: " + request.getCategoryId(),
                    HttpStatus.NOT_FOUND);
        }

        ResponseObject<?> responseObject = userClient.getUser(request.getTeacherId());
        Optional<Object> userOptional = Optional.ofNullable(responseObject.getData());
        if (userOptional.isEmpty()) {
            return ResponseObject.ofData(null,
                    "Không tìm thấy giáo viên với ID: " + request.getTeacherId(),
                    HttpStatus.NOT_FOUND);
        }

        Course course = new Course();
        course.setCourseTitle(request.getCourseTitle());
        course.setCategoryId(request.getCategoryId());
        course.setDescription(request.getDescription());
        course.setTeacherId(request.getTeacherId());
        course.setPrice(request.getPrice());
        courseRepository.save(course);
        return ResponseObject.ofData(null, "Thêm khóa học thành công.");
    }

    @Override
    public ResponseObject<?> putCourse(String id, COPutCourseRequest request) {

        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isEmpty()) {
            return ResponseObject.ofData(null, "Không tìm thấy khóa học với ID: " + id, HttpStatus.NOT_FOUND);
        }
        Course course = courseOptional.get();
        course.setCourseTitle(request.getCourseTitle());
        course.setCategoryId(request.getCategoryId());
        course.setDescription(request.getDescription());
        course.setTeacherId(request.getTeacherId());
        course.setPrice(request.getPrice());
        courseRepository.save(course);
        return ResponseObject.ofData(null, "Cập nhập khóa học thành công.");
    }

    @Override
    public ResponseObject<?> deleteCourse(String id) {

        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isEmpty()) {
            return ResponseObject.ofData(null, "Không tìm thấy khóa học với ID: " + id, HttpStatus.NOT_FOUND);
        }
        Course course = courseOptional.get();
        course.setIsDeleted(!course.getIsDeleted());
        courseRepository.save(course);
        return ResponseObject.ofData(null, "Cập nhập trạng thái khóa học thành công.");
    }

    @Override
    public ResponseObject<?> getCourse(String id) {

        List<COCourseResponse> courseById = courseRepository.getCourseById(id);
        if (courseById.isEmpty()) {
            return ResponseObject.ofData(null, "Không tìm thấy khóa học với ID: " + id, HttpStatus.NOT_FOUND);
        }
        COCourseResponse courseResponse = courseById.get(0);
        ResponseObject<CUserResponse> responseObject = userClient.getUser(courseResponse.getTeacherId());
        Optional<Object> userOptional = Optional.ofNullable(responseObject.getData());
        if (userOptional.isEmpty()) {
            return ResponseObject.ofData(null,
                    "Không tìm thấy giáo viên với ID: " + courseResponse.getTeacherId(),
                    HttpStatus.NOT_FOUND);
        }

        COCourseDetailResponse courseDetailResponse = MergeUtils.merge(courseResponse,
                List.of((com.hungvt.courseservice.core.client.model.response.CUserResponse) userOptional.get()));

        return ResponseObject.ofData(courseDetailResponse);
    }

    @Override
    public ResponseObject<?> getSearchCourses(COGetSearchCourseRequest request) {

        Pageable pageable = Helper.createPageable(request);
        PageableObject<COCourseResponse> pageableSearchCourses = PageableObject.of(
                courseRepository.getSearchCourses(pageable, request)
        );

        List<String> userIds = pageableSearchCourses.getData().stream().map(COCourseResponse::getTeacherId).toList();
        ResponseObject<List<CUserResponse>> usersResponseObject = userClient.getBulkUser(userIds);
        List<CUserResponse> users = usersResponseObject.getData();

        List<COCourseDetailResponse> courseDetailResponses = pageableSearchCourses.getData().stream()
                .map(courseResponse -> MergeUtils.merge(courseResponse, users))
                .toList();

        PageableObject<COCourseDetailResponse> response = new PageableObject<>(
                courseDetailResponses,
                pageableSearchCourses.getTotalPages(),
                pageableSearchCourses.getCurrentPage(),
                pageableSearchCourses.getTotalElements());

        return ResponseObject.ofData(response, "Lấy danh sách thành công.");
    }

}
