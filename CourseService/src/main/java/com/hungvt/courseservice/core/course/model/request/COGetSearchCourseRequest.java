package com.hungvt.courseservice.core.course.model.request;

import com.hungvt.courseservice.infrastructure.model.request.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class COGetSearchCourseRequest extends PageableRequest {

    private Boolean isDeleted;


}
