package com.UrbanNest.master.service.serviceImpl;

import com.UrbanNest.master.dto.*;
import com.UrbanNest.master.entity.PersonEntity;
import com.UrbanNest.master.entity.PersonRoleMappingEntity;
import com.UrbanNest.master.entity.RoleEntity;
import com.UrbanNest.master.enums.Roles;
import com.UrbanNest.master.repository.PersonRepository;
import com.UrbanNest.master.repository.PersonRoleRepository;
import com.UrbanNest.master.repository.RoleRepository;
import com.UrbanNest.master.service.IamService;
import com.UrbanNest.master.service.OwnerService;
import com.UrbanNest.master.service.PersonService;
import jakarta.transaction.Transactional;
import org.glassfish.jaxb.runtime.v2.runtime.output.SAXOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private final OwnerService ownerService;
    private final IamService iamService;
    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;
    private final PersonRoleRepository personRoleRepository;

    public PersonServiceImpl(OwnerService ownerService, IamService iamService, RoleRepository roleRepository, PersonRepository personRepository, PersonRoleRepository personRoleRepository) {
        this.ownerService = ownerService;
        this.iamService = iamService;
        this.roleRepository = roleRepository;
        this.personRepository = personRepository;
        this.personRoleRepository = personRoleRepository;
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
            System.out.println("psi----------------------------------------------------------------------------------------------------------------------------");
        }

        // Now create IAM user after saving the person entity
        String iamId = iamService.addUser(personDto, password);
        //personRepository.save(savedPersonEntity); // Save updated entity
        return personDto;
    }

            // -- Only add person, no username password, iamuser not included -- //
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
    
    private void savePersonRoleMapping(List<Roles> roles, PersonEntity personEntity){
    List<PersonRoleMappingEntity> personRoleMappingEntities =new ArrayList<>();
    List<Long> roleIds = roleRepository.getRoleIdsByRoleNames(roles.stream().map(Enum::name).toList());
    if(!CollectionUtils.isEmpty(roles)){
        for (int i =0; i<roles.size();i++){
            PersonRoleMappingEntity personRoleMappingEntity = PersonRoleMappingEntity.builder()
                    .userId(personEntity.getId())
                    .roleId(roleIds.get(i))
                    .build();
            personRoleMappingEntities.add(personRoleMappingEntity);
        }
        personRoleRepository.saveAll(personRoleMappingEntities);
    }
    }




}
