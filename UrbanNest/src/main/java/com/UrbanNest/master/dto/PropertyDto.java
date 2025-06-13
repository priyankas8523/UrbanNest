package com.UrbanNest.master.dto;

import com.UrbanNest.master.entity.OwnerEntity;
import com.UrbanNest.master.entity.PropertyEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyDto {

    private Long id;

    private String propName;

    private OwnerEntity owner;

    private String address;

    public PropertyDto(Object[] objects) {
        this.propName = (String) objects[1];
        this.owner = (OwnerEntity) objects[2];
        this.address = (String) objects[3];

    }
}
