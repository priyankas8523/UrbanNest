package com.UrbanNest.master.interceptor;


import com.UrbanNest.master.annotations.IsAuthorize;
import com.UrbanNest.master.enums.ResponseCode;
import com.UrbanNest.master.enums.Roles;
import com.UrbanNest.master.exception.UrbanNestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

public class AuthorizationAspect {
    
    @Value("${keycloak.realm}")
    private String realm;
    
    @Autowired
    private Keycloak keycloak;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Before("@annotation(isAuthorized)")
    public void preHandle(JoinPoint joinPoint, IsAuthorize isAuthorize) throws UrbanNestException {
        String permissionKey =isAuthorize.value();
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt)authentication.getPrincipal();
        Map<String, Object> attributes = jwt.getClaims();
        
        List<String> userRoles = (List<String>) objectMapper.convertValue(attributes.get("realm_access"), HashMap.class).get("roles");
        Set<String> preDefinedRoles = Arrays.stream(Roles.values()).map(Enum::name).collect(Collectors.toSet());
        userRoles = userRoles.stream().filter(preDefinedRoles::contains).collect(Collectors.toList());
        
        if (userRoles.getFirst().equals(Roles.ADMIN.name())) {
            return;
        }
        
        Set<String> allPermissions = getAllMergePermissions(userRoles);
        
        if (!allPermissions.contains(permissionKey)) {
            throw new UrbanNestException( ResponseCode.ACCESS_DENIED, "You are not authorize to perform this operation");
        }
        
    }
    private Set<String> getAllMergePermissions(List<String> userRoles) {
        
        Set<String> permissionSet = new HashSet<>();
        
        //Collect all the permissions assigned to a role
        userRoles.stream()
                .peek(userRole -> {
                    Map<String, List<String>> stringListMap = keycloak.realm(realm).roles().get(userRole).toRepresentation().getAttributes();
                    if (Objects.nonNull(stringListMap)) {
                        permissionSet.addAll(stringListMap.keySet());
                    }
                });
        
        return permissionSet;
    }
    
}

    

