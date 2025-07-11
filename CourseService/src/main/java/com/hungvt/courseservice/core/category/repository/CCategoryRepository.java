package com.hungvt.courseservice.core.category.repository;

import com.hungvt.courseservice.core.category.model.request.CGetSearchCategoriesRequest;
import com.hungvt.courseservice.core.category.model.response.CCategoryResponse;
import com.hungvt.courseservice.entity.Category;
import com.hungvt.courseservice.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CCategoryRepository extends CategoryRepository {

    Optional<Category> findByCategoryCode(String categoryCode);

    @Query(value = """
            SELECT
                c.id,
                c.categoryCode,
                c.categoryName,
                c.description,
                c.isDeleted
            FROM category c WHERE c.id = :id
            """, nativeQuery = true)
    List<CCategoryResponse> getCategoryById(String id);

    @Query(value = """
            SELECT
                c.id,
                c.categoryCode,
                c.categoryName,
                c.description,
                c.isDeleted
            FROM
                category c
            WHERE
                (:#{#request.q} IS NULL OR c.id LIKE CONCAT('%', :#{#request.q}, '%')
                OR c.categoryCode LIKE CONCAT('%', :#{#request.q}, '%')
                OR c.categoryName LIKE CONCAT('%', :#{#request.q}, '%')
                OR c.description LIKE CONCAT('%', :#{#request.q}, '%'))
                AND (:#{#request.isDeleted} IS NULL OR c.isDeleted LIKE CONCAT('%', :#{#request.isDeleted}, '%'))
            """, nativeQuery = true)
    Page<CCategoryResponse> getSearchCategories(Pageable pageable, CGetSearchCategoriesRequest request);

}
