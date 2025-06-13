package com.UrbanNest.master.repository;

import com.UrbanNest.master.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerEntity,Long> {
//    @Query(nativeQuery = true, value = """
//            Select
//            o.email,
//            o.firstname,
//            o.lastname
//            from Owner o
//            where o.id = :id """)
//    Optional<OwnerEntity> getOwnerById(int id);
}
