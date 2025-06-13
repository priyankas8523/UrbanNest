package com.UrbanNest.master.entity;

import com.UrbanNest.master.dto.UnitDto;
import com.UrbanNest.master.enums.UnitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Unit")
@Builder
public class UnitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String unitNo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prop_id", referencedColumnName = "id")
    private PropertyEntity propertyEntity;

    @Enumerated(EnumType.STRING)
//    @ManyToOne
//    @JoinColumn(name = "ut_id", referencedColumnName = "id")
    private UnitType unitType;

    private boolean rented;

    private double rent;

    public static UnitDto mapToUnitDto(UnitEntity unitEntity){
        return UnitDto.builder()
                .unitNo(unitEntity.getUnitNo())
                .property_Id(unitEntity.getPropertyEntity().getId())
                .unitType(unitEntity.getUnitType())
                .rented(unitEntity.isRented())
                .rent(unitEntity.getRent())
                .build();
    }

    public static UnitEntity mapToEntity(UnitDto unitDto){
        return UnitEntity.builder()
                .unitNo(unitDto.getUnitNo())
                .unitType(unitDto.getUnitType())
                .rented(unitDto.isRented())
                .rent(unitDto.getRent())
                .build();
    }

    public static UnitEntity toUpdate(UnitEntity unitEntity, UnitDto unitDto){
        unitEntity.setUnitNo(unitDto.getUnitNo());
        unitEntity.setUnitType(unitDto.getUnitType());
        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setId(unitDto.getProperty_Id());
        unitEntity.setPropertyEntity(propertyEntity);
        unitEntity.setRented(unitDto.isRented());
        unitEntity.setRent(unitDto.getRent());
        return unitEntity;
    }

}
