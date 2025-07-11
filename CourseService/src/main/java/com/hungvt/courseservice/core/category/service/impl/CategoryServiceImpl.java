package com.hungvt.courseservice.core.category.service.impl;

import com.hungvt.courseservice.core.category.model.request.CGetSearchCategoriesRequest;
import com.hungvt.courseservice.core.category.model.request.CPostCategoryRequest;
import com.hungvt.courseservice.core.category.model.request.CPutCategoryRequest;
import com.hungvt.courseservice.core.category.model.response.CCategoryResponse;
import com.hungvt.courseservice.core.category.repository.CCategoryRepository;
import com.hungvt.courseservice.core.category.service.CategoryService;
import com.hungvt.courseservice.core.client.UserClient;
import com.hungvt.courseservice.entity.Category;
import com.hungvt.courseservice.infrastructure.model.response.PageableObject;
import com.hungvt.courseservice.infrastructure.model.response.ResponseObject;
import com.hungvt.courseservice.infrastructure.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final UserClient userClient;

    private final CCategoryRepository categoryRepository;

    @Override
    public ResponseObject postCategory(CPostCategoryRequest request) {

        Optional<Category> categoryOptional = categoryRepository.findByCategoryCode(request.getCategoryCode());
        if (categoryOptional.isPresent()) {
            return ResponseObject.ofData(null, "Mã thể loại đã tồn tại: " + request.getCategoryCode(), HttpStatus.CONFLICT);
        }

        Category category = new Category();
        category.setCategoryCode(request.getCategoryCode());
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
        return ResponseObject.ofData(null, "Thêm thể loại thành công.");
    }

    @Override
    public ResponseObject putCategory(String id, CPutCategoryRequest request) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            return ResponseObject.ofData(null, "Không tìm thấy thể loại với ID: " + id, HttpStatus.NOT_FOUND);
        }
        Category category = categoryOptional.get();
        category.setCategoryCode(request.getCategoryCode());
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
        return ResponseObject.ofData(null, "Cập nhập thể loại thành công.");
    }

    @Override
    public ResponseObject deleteCategory(String id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            return ResponseObject.ofData(null, "Không tìm thấy thể loại với ID: " + id, HttpStatus.NOT_FOUND);
        }
        Category category = categoryOptional.get();
        category.setIsDeleted(!category.getIsDeleted());
        categoryRepository.save(category);
        return ResponseObject.ofData(null, "Cập nhập trạng thái thể loại thành công.");
    }

    @Override
    public ResponseObject getCategory(String id) {

        List<CCategoryResponse> categoryById = categoryRepository.getCategoryById(id);
        if (categoryById.isEmpty()) {
            return ResponseObject.ofData(null, "Không tìm thấy thể loại với ID: " + id, HttpStatus.NOT_FOUND);
        }
        return ResponseObject.ofData(categoryById.get(0));
    }

    @Override
    public ResponseObject getSearchCategories(CGetSearchCategoriesRequest request) {

        Pageable pageable = Helper.createPageable(request);
        return ResponseObject.ofData(
                PageableObject.of(
                        categoryRepository.getSearchCategories(pageable, request)
                ), "Lấy danh sách thành công.");
    }

}
