package com.kaiostavares.spring_security_auth_api.infra.security;

import com.kaiostavares.spring_security_auth_api.persistent.enums.RoleEnum;
import com.kaiostavares.spring_security_auth_api.persistent.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserAuthenticated implements UserDetails {
    private final User user;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getRole() == RoleEnum.ADMIN ?
                List.of(
                        this.getRole(RoleEnum.ADMIN),
                        this.getRole(RoleEnum.USER)) :
                List.of(this.getRole(RoleEnum.USER));
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private SimpleGrantedAuthority getRole(RoleEnum role){
        return new SimpleGrantedAuthority("ROLE_" + role.getRoleInfo());
    }
}