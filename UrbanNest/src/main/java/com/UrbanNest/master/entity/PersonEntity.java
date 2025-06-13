package com.UrbanNest.master.entity;

import com.UrbanNest.master.dto.PersonDto;
import com.UrbanNest.master.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
@SuperBuilder
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String iamId;

    private String firstName;

    private String lastName;

    private String email;

    @Enumerated(EnumType.STRING)
    private Roles role;

    public static PersonEntity mapToPersonEntity(PersonDto personDto){
        return PersonEntity.builder()
                .iamId(personDto.getIamId())
                .firstName(personDto.getFirstName())
                .lastName(personDto.getLastName())
                .email(personDto.getEmail())
                .role(personDto.getRole())
                .build();

    }

    public static PersonDto mapToPersonDto(PersonEntity personEntity){
        return PersonDto.builder()
                .iamId(personEntity.getIamId())
                .firstName(personEntity.getFirstName())
                .lastName(personEntity.getLastName())
                .email(personEntity.getEmail())
                .role(personEntity.getRole())
                .build();
    }



}
