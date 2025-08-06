package com.hungvt.courseservice.entity;

import com.hungvt.courseservice.entity.base.PrimaryEntity;
import com.hungvt.courseservice.infrastructure.constant.EnrollmentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "enrollment")
public class Enrollment extends PrimaryEntity {

    private String studentId;

    private String courseId;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

}
