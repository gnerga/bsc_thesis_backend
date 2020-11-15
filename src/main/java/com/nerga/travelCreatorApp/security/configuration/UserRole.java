package com.nerga.travelCreatorApp.security.configuration;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

import static com.nerga.travelCreatorApp.security.configuration.UserPermission.*;

public enum UserRole {

    USER(Sets.newHashSet(
            LOCATION_READ,
            LOCATION_WRITE,
            TRIP_READ,
            TRIP_WRITE
    ));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions){
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority(){
        return null;
    }

}
