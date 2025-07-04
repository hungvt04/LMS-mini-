package com.hungvt.userservice.core.auth.repository;

import com.hungvt.userservice.core.auth.model.response.AuthProfileResponse;
import com.hungvt.userservice.repository.UserRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthUserRepository extends UserRepository {

    @Query(value = """
            SELECT 
                u.id AS id,
                u.username AS username,
                u.email AS email,
                u.full_name AS fullName,
                u.avatar AS avatar,
                u.created_at AS createdAt,
                u.updated_at AS updatedAt
                WHERE u.id = :id AND u.is_deleted = false
            """, nativeQuery = true)
    List<AuthProfileResponse> findProfileById(String id);

}
