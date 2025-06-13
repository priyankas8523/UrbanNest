package com.UrbanNest.master.controller;

import com.UrbanNest.master.dto.UnitDto;
import com.UrbanNest.master.entity.UnitEntity;
import com.UrbanNest.master.service.UnitService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/unit")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<String> addUnit(@RequestBody UnitDto unitDto){
        unitService.addUnit(unitDto);
        return ResponseEntity.ok("Unit added!!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeUnit(@PathVariable Long id){
        unitService.deleteUnit(id);
        //String unitName =
        return ResponseEntity.ok("Unit deleted successfully!!");
    }

    @GetMapping
    public Page<UnitDto> getAllUnits(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int pageSize){
        System.out.println("Inside getallunit "+ Thread.currentThread().getName());
        unitService.asyncMethodTest();
            return unitService.getAllUnits(page, pageSize);

    }

}
