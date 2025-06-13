package com.UrbanNest.master.dto;

import com.UrbanNest.master.entity.PersonEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerDto {
    private String firstName;

    private String lastName;

    private String email;

    private PersonEntity personEntity;
}
