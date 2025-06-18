package com.UrbanNest.master.repository;

import com.UrbanNest.master.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    
    @Query(nativeQuery = true, value = """
            SELECT r.id FROM roles r
            WHERE r.role_name IN :roleNames
            """)
    List<Long> getRoleIdsByRoleNames(List<String> roleNames);
    
}
