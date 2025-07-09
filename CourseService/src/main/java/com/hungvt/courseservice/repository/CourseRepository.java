package com.hungvt.courseservice.repository;

import com.hungvt.courseservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String>{
}
