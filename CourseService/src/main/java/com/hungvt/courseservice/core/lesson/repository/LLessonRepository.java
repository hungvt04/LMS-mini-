package com.hungvt.courseservice.core.lesson.repository;

import com.hungvt.courseservice.core.lesson.model.request.CGetSearchCategoriesRequest;
import com.hungvt.courseservice.core.lesson.model.response.CLessonResponse;
import com.hungvt.courseservice.entity.Lesson;
import com.hungvt.courseservice.repository.LessonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LLessonRepository extends LessonRepository {

    Optional<Lesson> findByCourseIdAndOrderNo(String courseId, int orderNo);

    @Query(value = """
            SELECT
                l.id,
                l.course_id,
                l.title,
                l.content,
                l.video_url,
                l.order_no
            FROM lesson l WHERE l.id = :id
            """, nativeQuery = true)
    List<CLessonResponse> getLessonById(String id);

    @Query(value = """
            SELECT
                l.id,
                l.course_id,
                l.title,
                l.content,
                l.video_url,
                l.order_no
            FROM
                lesson l
            WHERE
                (:#{#request.q} IS NULL OR l.id LIKE CONCAT('%', :#{#request.q}, '%')
                OR l.courseId LIKE CONCAT('%', :#{#request.q}, '%')
                OR l.title LIKE CONCAT('%', :#{#request.q}, '%')
                OR l.content LIKE CONCAT('%', :#{#request.q}, '%')
                OR l.videoUrl LIKE CONCAT('%', :#{#request.q}, '%'))
                AND (:#{#request.isDeleted} IS NULL OR l.isDeleted LIKE CONCAT('%', :#{#request.isDeleted}, '%'))
            """, nativeQuery = true)
    Page<CLessonResponse> getSearchLessons(Pageable pageable, CGetSearchCategoriesRequest request);

}
