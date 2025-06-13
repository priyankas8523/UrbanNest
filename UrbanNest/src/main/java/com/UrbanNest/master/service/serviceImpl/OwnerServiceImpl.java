package com.UrbanNest.master.service.serviceImpl;

import com.UrbanNest.master.dto.OwnerDto;
import com.UrbanNest.master.entity.OwnerEntity;
import com.UrbanNest.master.entity.PersonEntity;
import com.UrbanNest.master.repository.OwnerRepository;
import com.UrbanNest.master.repository.PersonRepository;
import com.UrbanNest.master.service.OwnerService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final PersonRepository personRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository, PersonRepository personRepository) {
        this.ownerRepository = ownerRepository;
        this.personRepository = personRepository;
    }

    @Override
    public OwnerEntity findOwnerByid(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Owner not found with id "+ id));
    }

    @Override
    public void createOwner(OwnerDto ownerDto) {
        Long id = ownerDto.getPersonEntity().getId();
        Optional<PersonEntity> optionalPersonEntity = personRepository.findById(id);
        OwnerEntity ownerEntity = OwnerEntity.mapToOwnerEntity(ownerDto);
        if (optionalPersonEntity.isPresent()) {
            ownerEntity.setPersonEntity(optionalPersonEntity.get());
        }//adding person_id in owner
        ownerRepository.save(ownerEntity);

    }


}
