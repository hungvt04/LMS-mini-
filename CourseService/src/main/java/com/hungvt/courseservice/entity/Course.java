package com.hungvt.courseservice.entity;

import com.hungvt.courseservice.entity.base.PrimaryEntity;
import jakarta.persistence.Entity;
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
@Table(name = "course")
public class Course extends PrimaryEntity {

    private String courseTitle;

    private String description;

    private Float price;

    private String categoryId;

    private String teacherId;

}
