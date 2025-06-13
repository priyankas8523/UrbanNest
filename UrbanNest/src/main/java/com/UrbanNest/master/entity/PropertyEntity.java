package com.UrbanNest.master.entity;

import com.UrbanNest.master.dto.PropertyDto;
import com.UrbanNest.master.service.serviceImpl.OwnerServiceImpl;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "property")
@SuperBuilder
public class PropertyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String propName;

    @ManyToOne          //check
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private OwnerEntity owner;

    private String address;

    public static PropertyEntity mapToPropertyEntity(PropertyDto propertyDto, OwnerEntity ownerEntity) {
        return PropertyEntity.builder()
                .id(propertyDto.getId())
                .propName(propertyDto.getPropName())
                .owner(ownerEntity) //i want to take existing owner from database here
                .address(propertyDto.getAddress())
                .build();
    }

    public static PropertyDto mapToPropertyDto(PropertyEntity propertyEntity){
        return PropertyDto.builder()
                .id(propertyEntity.getId())
                .propName(propertyEntity.getPropName())
                .owner(propertyEntity.getOwner())
                .address(propertyEntity.getAddress())
                .build();
    }


    }



