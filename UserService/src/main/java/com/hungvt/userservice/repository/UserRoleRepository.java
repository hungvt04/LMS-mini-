package com.hungvt.userservice.repository;

import com.hungvt.userservice.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    @Query("""
            SELECT ur.role.roleName FROM UserRole ur
            WHERE ur.user.username = :username
                AND ur.role.isDeleted = false
            """)
    List<String> findByUser(String username);

}
