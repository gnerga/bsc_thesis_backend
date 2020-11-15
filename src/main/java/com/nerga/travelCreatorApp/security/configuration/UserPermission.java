package com.nerga.travelCreatorApp.security.configuration;

public enum UserPermission {

    LOCATION_READ("location:read"),
    LOCATION_WRITE("location:write"),
    TRIP_READ("trip:read"),
    TRIP_WRITE("trip:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
