package com.UrbanNest.master.controller;

import com.UrbanNest.master.dto.Response;
import com.UrbanNest.master.service.RolePermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/master/roles-and-permissions")
public class RolePermissionController {
    private final RolePermissionService rolePermissionService;
    
    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }
    
    @GetMapping
    ResponseEntity<?> getAllRolesAndPermissions() {
        Object response = rolePermissionService.getAllRolesAndPermissions();
        return ResponseEntity.ok(response);
    }
    
}
