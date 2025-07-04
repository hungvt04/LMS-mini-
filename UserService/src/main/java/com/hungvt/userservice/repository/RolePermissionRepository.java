package com.hungvt.userservice.repository;

import com.hungvt.userservice.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, String> {

    @Query("""
            SELECT
                p.permissionCode
            FROM RolePermission rp
                JOIN Permission p ON rp.permission.id = p.id
                JOIN Role r ON rp.role.id = r.id
                JOIN UserRole ur ON r.id = ur.role.id
            WHERE (ur.user.username = :username OR
                  ur.user.email = :username)
            """)
    List<String> findByUser(String username);

}
