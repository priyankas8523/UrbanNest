package com.UrbanNest.master.service;

import com.UrbanNest.master.dto.PermissionDto;

import java.util.List;
import java.util.Map;

public interface RolePermissionService {
    
    Map<String, Map<String, List<PermissionDto>>>getAllRolesAndPermissions();
}
