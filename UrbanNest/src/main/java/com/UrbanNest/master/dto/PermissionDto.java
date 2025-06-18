package com.UrbanNest.master.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PermissionDto {
    
    private UUID uuid;
    private String name;
    private boolean status;
    private String group;
    
}

