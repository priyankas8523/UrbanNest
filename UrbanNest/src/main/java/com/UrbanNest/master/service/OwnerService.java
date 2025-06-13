package com.UrbanNest.master.service;


import com.UrbanNest.master.dto.OwnerDto;
import com.UrbanNest.master.entity.OwnerEntity;

import java.util.Optional;

public interface OwnerService {
    OwnerEntity findOwnerByid(Long id);

    void createOwner(OwnerDto ownerDto);

}
