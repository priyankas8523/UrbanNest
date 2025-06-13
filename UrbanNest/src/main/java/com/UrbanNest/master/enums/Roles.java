package com.UrbanNest.master.enums;

public enum Roles {

    OWNER("Owner"),
    MANAGER("Manager"),
    ADMIN("Admin");

    private final String roleName;

    Roles(String roleName){
        this.roleName = roleName;
    }

    public String getRole(){
        return roleName;
    }
}
