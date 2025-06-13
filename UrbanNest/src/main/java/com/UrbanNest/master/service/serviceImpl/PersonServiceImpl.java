package com.UrbanNest.master.service.serviceImpl;

import com.UrbanNest.master.dto.*;
import com.UrbanNest.master.entity.PersonEntity;
import com.UrbanNest.master.enums.Roles;
import com.UrbanNest.master.repository.PersonRepository;
import com.UrbanNest.master.service.IamService;
import com.UrbanNest.master.service.OwnerService;
import com.UrbanNest.master.service.PersonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private final OwnerService ownerService;
    private final IamService iamService;

    private final PersonRepository personRepository;

    public PersonServiceImpl(OwnerService ownerService, IamService iamService, PersonRepository personRepository) {
        this.ownerService = ownerService;
        this.iamService = iamService;
        this.personRepository = personRepository;
    }

    @Transactional
    public PersonDto addPerson(PersonDto personDto, ResetPasswordRequest password) {
        PersonEntity personEntity = PersonEntity.mapToPersonEntity(personDto);
        PersonEntity savedPersonEntity = personRepository.save(personEntity);

        if(personDto.getRole() == Roles.OWNER) {
            OwnerDto ownerDto = OwnerDto.builder()
                    .firstName(savedPersonEntity.getFirstName())
                    .lastName(savedPersonEntity.getLastName())
                    .email(savedPersonEntity.getEmail())
                    .personEntity(PersonEntity.builder()
                            .id(savedPersonEntity.getId())
                            .build())
                    .build();
            ownerService.createOwner(ownerDto);
        }

        // Now create IAM user after saving the person entity
        String iamId = iamService.addUser(personDto, password);

        return personDto;
    }

            // Only add person, no username password, iamuser not included //
//    public PersonDto addPerson(PersonDto personDto){
//        PersonEntity personEntity = PersonEntity.mapToPersonEntity(personDto);
//        PersonEntity savedPersonEntity = personRepository.save(personEntity);
//
//        if(personDto.getRole() == Roles.OWNER) {
//            OwnerDto ownerDto = OwnerDto.builder()
//                    .firstName(savedPersonEntity.getFirstName())
//                    .lastName(savedPersonEntity.getLastName())
//                    .email(savedPersonEntity.getEmail())
//                    .personEntity(PersonEntity.builder()
//                            .id(savedPersonEntity.getId())
//                            .build())
//                    .build();
//            ownerService.createOwner(ownerDto);
//        }
//        return personDto;
//    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Optional<PersonDto> user = iamService.findByEmail(resetPasswordRequest.getEmail());
        iamService.resetPassword(user.get(), resetPasswordRequest.getNewPassword());
    }

//    @Override
//    public void updateRole(String newRole, String email) {
//        Optional<PersonDto> user = iamService.findByEmail(email);
//    }

    @Override
    public LoginResponse getAccessToken(LoginRequest authRequest) {
        return iamService.getAccessToken(authRequest);
    }

    @Override
    public String logout() {
        return iamService.logout();
    }




}
