package com.nerga.travelCreatorApp.security.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@Builder(access = AccessLevel.PUBLIC)
public class User implements UserDetails {

    private String username;
    private String password;
    private Set<?extends GrantedAuthority> grantedAuthorities;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLock;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public User(
            String username,
            String password,
            Set<? extends GrantedAuthority> grantedAuthorities,
            boolean isAccountNonExpired,
            boolean isAccountNonLock,
            boolean isCredentialsNonExpired,
            boolean isEnabled,
            String firstName,
            String lastName,
            String email,
            String phoneNumber
    ) {
        this.username = username;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLock = isAccountNonLock;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

}
