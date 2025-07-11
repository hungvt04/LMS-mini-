package com.hungvt.courseservice.core.category.controller;

import com.hungvt.courseservice.core.category.model.request.CGetSearchCategoriesRequest;
import com.hungvt.courseservice.core.category.model.request.CPostCategoryRequest;
import com.hungvt.courseservice.core.category.model.request.CPutCategoryRequest;
import com.hungvt.courseservice.core.category.service.CategoryService;
import com.hungvt.courseservice.infrastructure.constant.MappingUrl;
import com.hungvt.courseservice.infrastructure.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MappingUrl.API_CATEGORY)
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> postCategory(@RequestBody CPostCategoryRequest request) {
        return Helper.createResponseEntity(categoryService.postCategory(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCategory(@PathVariable String id, @RequestBody CPutCategoryRequest request) {
        return Helper.createResponseEntity(categoryService.putCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        return Helper.createResponseEntity(categoryService.deleteCategory(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable String id) {
        return Helper.createResponseEntity(categoryService.getCategory(id));
    }

    @GetMapping
    public ResponseEntity<?> getSearchCategories(CGetSearchCategoriesRequest request) {
        return Helper.createResponseEntity(categoryService.getSearchCategories(request));
    }

}
