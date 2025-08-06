package com.hungvt.courseservice.core.lesson.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CPutLessonRequest {

    private String title;

    private String content;

    private String videoUrl;

    private int orderNo;

}
