package com.UrbanNest.master.repository;


import com.UrbanNest.master.dto.PropertyDto;
import com.UrbanNest.master.dto.UnitDto;
import com.UrbanNest.master.entity.PropertyEntity;
import com.UrbanNest.master.entity.UnitEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = """
            DELETE from unit 
            WHERE id = :unitId
            """)
    void removeUnitById(Long unitId);

    @Query(nativeQuery = true, value = """
            SELECT 
             unit_no,
             rent,
             rented
            from unit
            """)
    Page<Object[]> getAllUnits(Pageable pageable);





    }
