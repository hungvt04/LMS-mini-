package com.hungvt.courseservice.core.course.repository;

import com.hungvt.courseservice.core.course.model.request.COGetSearchCourseRequest;
import com.hungvt.courseservice.core.course.model.response.COCourseResponse;
import com.hungvt.courseservice.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface COCourseRepository extends CourseRepository {

    @Query(value = """
            SELECT
                c.id,
                c.courseTitle,
                c.description,
                c.price,
                c.categoryId,
                ca.categoryName,
                c.teacherId,
                c.isDeleted
            FROM
                course c JOIN category ca
                ON c.teacherId = ca.id
            WHERE c.id = :id
            """, nativeQuery = true)
    List<COCourseResponse> getCourseById(String id);

    @Query(value = """
            SELECT
                c.id,
                c.courseTitle,
                c.description,
                c.price,
                c.categoryId,
                ca.categoryName,
                c.teacherId,
                c.isDeleted
            FROM
                course c JOIN category ca
                ON c.teacherId = ca.id
            WHERE
                (:#{#request.q} IS NULL OR c.id LIKE CONCAT('%', :#{#request.q}, '%')
                OR c.courseTitle LIKE CONCAT('%', :#{#request.q}, '%')
                OR c.description LIKE CONCAT('%', :#{#request.q}, '%')
                OR ca.categoryName LIKE CONCAT('%', :#{#request.q}, '%'))
                AND (:#{#request.isDeleted} IS NULL OR c.isDeleted = :#{#request.isDeleted})
            """, nativeQuery = true)
    Page<COCourseResponse> getSearchCourses(Pageable pageable, COGetSearchCourseRequest request);

    @Query(value = """
            SELECT
                c.id,
                c.courseTitle,
                c.description,
                c.price,
                c.categoryId,
                ca.categoryName,
                c.teacherId,
                c.isDeleted
            FROM
                Course c JOIN Category ca
                ON c.teacherId = ca.id
            WHERE
                (:#{#request.q} IS NULL OR c.id LIKE CONCAT('%', :#{#request.q}, '%')
                OR c.courseTitle LIKE CONCAT('%', :#{#request.q}, '%')
                OR c.description LIKE CONCAT('%', :#{#request.q}, '%')
                OR ca.categoryName LIKE CONCAT('%', :#{#request.q}, '%'))
                AND (:#{#request.isDeleted} IS NULL OR c.isDeleted = :#{#request.isDeleted})
            """)
    Page<COCourseResponse> getSearchCourses1(Pageable pageable, COGetSearchCourseRequest request);

}
