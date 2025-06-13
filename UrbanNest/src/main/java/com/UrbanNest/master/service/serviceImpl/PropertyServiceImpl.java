package com.UrbanNest.master.service.serviceImpl;

import com.UrbanNest.master.dto.PropertyDto;
import com.UrbanNest.master.entity.OwnerEntity;
import com.UrbanNest.master.entity.PropertyEntity;
import com.UrbanNest.master.repository.PropertyRepository;
import com.UrbanNest.master.service.OwnerService;
import com.UrbanNest.master.service.PropertyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final OwnerService ownerService;

    private final PropertyRepository propertyRepository;

    public PropertyServiceImpl(OwnerService ownerService, PropertyRepository propertyRepository) {
        this.ownerService = ownerService;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public void addProperty(PropertyDto propertyDto) {
        Long id = propertyDto.getOwner().getId();
        OwnerEntity ownerEntity = ownerService.findOwnerByid(id);
        PropertyEntity propertyEntity = PropertyEntity.mapToPropertyEntity(propertyDto,ownerEntity);
        //propertyEntity.setOwner(ownerEntity);
        propertyRepository.save(propertyEntity);
    }
    @Override
    public Page<PropertyDto> getAllProperties(int page, int pageSize) { //rewrite this api
        Pageable pageable = PageRequest.of(page, pageSize);
        return propertyRepository.getAllProperties(pageable)
                .map(objArr -> {
                    PropertyDto dto = new PropertyDto();
                    dto.setId((Long) objArr[0]);
                    dto.setPropName((String) objArr[1]);
                    return dto;
                });
    }




}
