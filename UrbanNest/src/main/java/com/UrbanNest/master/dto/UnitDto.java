package com.UrbanNest.master.dto;

import com.UrbanNest.master.entity.PropertyEntity;
import com.UrbanNest.master.enums.UnitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitDto {

    private String unitNo;

    private Long property_Id;

    private UnitType unitType;

    private boolean rented;

    private double rent;
}
