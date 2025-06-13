package com.UrbanNest.master.controller;

import com.UrbanNest.master.dto.PropertyDto;
import com.UrbanNest.master.entity.PropertyEntity;
import com.UrbanNest.master.service.PropertyService;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prop")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }


    @PostMapping("/add")
    public ResponseEntity<String> createProperty(@RequestBody PropertyDto propertyDto){
        propertyService.addProperty(propertyDto);
        return ResponseEntity.ok("New Property created!!!");

    }

    @GetMapping
    public Page<PropertyDto> getAllProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize){
        return propertyService.getAllProperties(page, pageSize);
    }

}
