package com.UrbanNest.master.repository;

import com.UrbanNest.master.entity.RolePermissionMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionMappingRepository extends JpaRepository<RolePermissionMappingEntity, Long> {
}
