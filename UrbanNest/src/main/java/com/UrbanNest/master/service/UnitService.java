package com.UrbanNest.master.service;

import com.UrbanNest.master.dto.PropertyDto;
import com.UrbanNest.master.dto.UnitDto;
import org.springframework.data.domain.Page;

public interface UnitService {
    void addUnit(UnitDto unitDto);

    void deleteUnit(Long unitId);

    Page<UnitDto> getAllUnits(int page, int pageSize);

    void asyncMethodTest();



    }
