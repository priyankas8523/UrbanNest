package com.UrbanNest.master.enums;

public enum UnitType {

    BHK1("1 BHK"),
    BHK2("2 BHK"),
    BHK3("3 BHK"),
    STUDIO("Studio Apartment");

    private final String type;

    UnitType(String type){
        this.type = type;
    }
}
