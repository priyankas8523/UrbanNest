package com.UrbanNest.master.service.serviceImpl;

import com.UrbanNest.master.dto.PropertyDto;
import com.UrbanNest.master.dto.UnitDto;
import com.UrbanNest.master.entity.UnitEntity;
import com.UrbanNest.master.enums.UnitType;
import com.UrbanNest.master.repository.UnitRepository;
import com.UrbanNest.master.service.UnitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Async("taskExecutor")
    public void asyncMethodTest() {
        System.out.println("Inside async method: " + Thread.currentThread().getName());
    }


    @Override
    public void addUnit(UnitDto unitDto) {
        UnitEntity unitEntity = UnitEntity.mapToEntity(unitDto);
        unitRepository.save(unitEntity);

    }

    @Override
    public void deleteUnit(Long unitId) {
        unitRepository.removeUnitById(unitId);
    }

    @Override
    public Page<UnitDto> getAllUnits(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return unitRepository.getAllUnits(pageable)
                .map(objArr -> {
                    UnitDto dto = new UnitDto();
                    dto.setUnitNo((String) objArr[0]);
//                    dto.setUnitType((UnitType.) objArr[1]);
                    dto.setRent((Double) objArr[1]);
                    dto.setRented((boolean) objArr[2]);
                    return dto;
                });
        }

}
