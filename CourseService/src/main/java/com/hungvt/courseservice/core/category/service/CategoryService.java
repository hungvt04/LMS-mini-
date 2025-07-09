package com.hungvt.courseservice.core.category.service;

import com.hungvt.courseservice.core.category.model.request.CGetSearchCategoriesRequest;
import com.hungvt.courseservice.core.category.model.request.CPostCategoryRequest;
import com.hungvt.courseservice.core.category.model.request.CPutCategoryRequest;
import com.hungvt.courseservice.infrastructure.model.response.ResponseObject;

public interface CategoryService {

    ResponseObject postCategory(CPostCategoryRequest request);

    ResponseObject putCategory(String id, CPutCategoryRequest request);

    ResponseObject deleteCategory(String id);

    ResponseObject getCategory(String id);

    ResponseObject searchCategories(CGetSearchCategoriesRequest request);

}
