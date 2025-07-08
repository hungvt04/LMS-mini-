package com.hungvt.userservice.core.user.repository;

import com.hungvt.userservice.entity.User;
import com.hungvt.userservice.repository.UserRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UUserRepository extends UserRepository {

    @Query(value = """
            SELECT
                u
            FROM User u JOIN UserRole ur
                ON u.id = ur.user.id
            WHERE ur.role.roleRank < :roleRank
            """)
    List<User> getBulkByRoleRank(int roleRank);

}
