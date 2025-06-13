package com.UrbanNest.master.entity;

import com.UrbanNest.master.dto.OwnerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "owner")
@Builder
public class OwnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private PersonEntity personEntity;

    public static OwnerEntity mapToOwnerEntity(OwnerDto ownerDto){
        return OwnerEntity.builder()
                .firstName(ownerDto.getFirstName())
                .lastName(ownerDto.getLastName())
                .email(ownerDto.getEmail())
                .personEntity(ownerDto.getPersonEntity())
                .build();
    }

    public static OwnerDto mapToOwnerDto(OwnerEntity ownerEntity){
        return OwnerDto.builder()
                .firstName(ownerEntity.getFirstName())
                .lastName(ownerEntity.getLastName())
                .email(ownerEntity.getEmail())
                .personEntity(ownerEntity.getPersonEntity())
                .build();
    }


}
