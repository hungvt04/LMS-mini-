package com.hungvt.courseservice.core.lesson.service;

import com.hungvt.courseservice.core.lesson.model.request.CGetSearchCategoriesRequest;
import com.hungvt.courseservice.core.lesson.model.request.CPutLessonRequest;
import com.hungvt.courseservice.core.lesson.model.request.LPostLessonRequest;
import com.hungvt.courseservice.infrastructure.model.response.ResponseObject;

public interface LessonService {

    ResponseObject<?> postLesson(LPostLessonRequest request);

    ResponseObject<?> putLesson(String id, CPutLessonRequest request);

    ResponseObject<?> deleteLesson(String id);

    ResponseObject<?> getLesson(String id);

    ResponseObject<?> getSearchLessons(CGetSearchCategoriesRequest request);

}
