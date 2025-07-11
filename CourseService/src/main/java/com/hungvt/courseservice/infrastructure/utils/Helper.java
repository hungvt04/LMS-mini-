package com.hungvt.courseservice.infrastructure.utils;

import com.hungvt.courseservice.infrastructure.constant.PaginationConstant;
import com.hungvt.courseservice.infrastructure.model.request.PageableRequest;
import com.hungvt.courseservice.infrastructure.model.response.ResponseObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

public class Helper {

    public static Pageable createPageable(PageableRequest request) {
        int page = request.getPage() - 1;
        int size = request.getSize() == 0 ? PaginationConstant.DEFAULT_SIZE : request.getSize();

        Sort.Direction direction = (Sort.Direction.fromString(
                request.getOrderBy()) == Sort.Direction.DESC ||
                request.getOrderBy() == null ||
                request.getOrderBy().isBlank()
        ) ? Sort.Direction.DESC : Sort.Direction.ASC;

        String sortBy = request.getSortBy();
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = PaginationConstant.DEFAULT_SORT_BY;
        }
        return PageRequest.of(page, size, Sort.by(direction, sortBy));
    }

    public static ResponseEntity<?> createResponseEntity(ResponseObject responseObject) {
        return new ResponseEntity<>(responseObject, responseObject.getStatus());
    }

}
