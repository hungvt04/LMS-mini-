package com.hungvt.userservice.core.user.repository;

import com.hungvt.userservice.repository.RoleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface URoleRepository extends RoleRepository {

    @Query("""
            SELECT
                r.roleRank
            FROM Role r JOIN UserRole ur
                ON r.id = ur.role.id
            WHERE ur.user.id = :userId
            """)
    List<Integer> findByUserId(String userId);

}
