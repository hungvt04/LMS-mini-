package com.hungvt.courseservice.core.category.service.impl;

import com.hungvt.courseservice.core.category.model.request.CGetSearchCategoriesRequest;
import com.hungvt.courseservice.core.category.model.request.CPostCategoryRequest;
import com.hungvt.courseservice.core.category.model.request.CPutCategoryRequest;
import com.hungvt.courseservice.core.category.repository.CCategoryRepository;
import com.hungvt.courseservice.core.category.service.CategoryService;
import com.hungvt.courseservice.core.client.UserClient;
import com.hungvt.courseservice.infrastructure.model.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final UserClient userClient;

    private final CCategoryRepository categoryRepository;

    @Override
    public ResponseObject postCategory(CPostCategoryRequest request) {
        return null;
    }

    @Override
    public ResponseObject putCategory(String id, CPutCategoryRequest request) {
        return null;
    }

    @Override
    public ResponseObject deleteCategory(String id) {
        return null;
    }

    @Override
    public ResponseObject getCategory(String id) {
        return null;
    }

    @Override
    public ResponseObject searchCategories(CGetSearchCategoriesRequest request) {
        return null;
    }

}
