package com.UrbanNest.master.repository;

import com.UrbanNest.master.entity.OwnerEntity;
import com.UrbanNest.master.entity.PropertyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

    @Query(nativeQuery = true, value = """
            Select
            p.id,
            p.prop_name
            from property p
            """)

    Page<Object[]> getAllProperties(Pageable pageable);





}
