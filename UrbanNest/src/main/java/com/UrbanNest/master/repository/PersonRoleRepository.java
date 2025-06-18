package com.UrbanNest.master.repository;

import com.UrbanNest.master.entity.PersonRoleMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRoleRepository extends JpaRepository<PersonRoleMappingEntity, Long> {

}
