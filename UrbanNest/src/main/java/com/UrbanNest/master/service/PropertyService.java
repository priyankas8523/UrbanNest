package com.UrbanNest.master.service;

import com.UrbanNest.master.dto.PersonDto;
import com.UrbanNest.master.dto.PropertyDto;
import org.springframework.data.domain.Page;

public interface PropertyService {
    void addProperty(PropertyDto propertyDto);

    Page<PropertyDto> getAllProperties(int page, int pageSize);
}
