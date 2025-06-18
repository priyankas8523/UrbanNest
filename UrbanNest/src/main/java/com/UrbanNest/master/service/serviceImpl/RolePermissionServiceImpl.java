package com.UrbanNest.master.service.serviceImpl;

import com.UrbanNest.master.dto.PermissionDto;
import com.UrbanNest.master.entity.PermissionEntity;
import com.UrbanNest.master.entity.RoleEntity;
import com.UrbanNest.master.repository.PermissionRepository;
import com.UrbanNest.master.repository.RolePermissionMappingRepository;
import com.UrbanNest.master.repository.RoleRepository;
import com.UrbanNest.master.service.RolePermissionService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    
    private final RolePermissionMappingRepository rolePermissionMappingRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final IamServiceImpl iamService;
    
    public RolePermissionServiceImpl(RolePermissionMappingRepository rolePermissionMappingRepository,
                                     RoleRepository roleRepository,
                                     PermissionRepository permissionRepository,
                                     IamServiceImpl iamService) {
        this.rolePermissionMappingRepository = rolePermissionMappingRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.iamService = iamService;
    }
    
    
    @Override
    public Map<String, Map<String, List<PermissionDto>>> getAllRolesAndPermissions() {
        List<Object[]> rolePermissionMappingObjects = rolePermissionMappingRepository.getAllRolesPermissions();
        Map<String, Set<String>> map = rolePermissionMappingObjects.stream()
                .collect(Collectors.toMap(
                        row -> String.valueOf(row[0]), // Key: Convert role name to String
                        row -> Arrays.stream((String[]) row[1]) // Value: Stream over String[] permissions
                                .collect(Collectors.toSet()) // Convert to Set<String>
                ));
        
        List<PermissionEntity> permissionEntities = permissionRepository.findAll();
        List<RoleEntity> roleEntities = roleRepository.findAll();
        
        return roleEntities.stream()
                .collect(Collectors.toMap(RoleEntity::getRoleName, roleEntity -> permissionEntities.stream()
                        .map(permissionEntity -> {
                            Set<String> permissionSet = map.get(roleEntity.getRoleName());
                            return PermissionDto.builder()
                                    .name(permissionEntity.getPermission())
                                    .status(Objects.nonNull(permissionSet) && permissionSet.contains(permissionEntity.getPermission()))
                                    //.group(permissionEntity.getGroup())
                                    .build();
                        })
                        .collect(Collectors.groupingBy(PermissionDto::getGroup, TreeMap::new, Collectors.toList()))));
                
    }
}
