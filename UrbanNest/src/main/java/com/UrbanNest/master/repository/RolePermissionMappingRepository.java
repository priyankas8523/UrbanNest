package com.UrbanNest.master.repository;

import com.UrbanNest.master.entity.RolePermissionMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionMappingRepository extends JpaRepository<RolePermissionMappingEntity, Long> {
    
    @Query(nativeQuery = true, value = """
            SELECT r.role_name
            	,Array_agg(p.permission)
            FROM ROLE r
            JOIN role_permission_mapping rpm ON r.id = rpm.role_id
            JOIN permission p ON rpm.permission_id = p.id
            GROUP BY r.role_name
            """)
    List<Object[]> getAllRolesPermissions();
}
