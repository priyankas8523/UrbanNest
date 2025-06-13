package com.UrbanNest.master.dto;

import com.UrbanNest.master.entity.RolesEntity;
import com.UrbanNest.master.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDto {

    private String iamId;

    private String firstName;

    private String lastName;

    private String email;

    private Roles role;

}
