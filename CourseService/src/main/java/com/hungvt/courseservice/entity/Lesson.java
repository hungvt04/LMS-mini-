package com.hungvt.courseservice.entity;

import com.hungvt.courseservice.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
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
@Table(name = "lesson")
public class Lesson extends PrimaryEntity {

    private String courseId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String videoUrl;

    private int orderNo;

}
