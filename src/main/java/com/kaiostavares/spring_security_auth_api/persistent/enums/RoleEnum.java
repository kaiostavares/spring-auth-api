package com.kaiostavares.spring_security_auth_api.persistent.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER");

    private final String roleInfo;

    RoleEnum(String roleInfo) {
        this.roleInfo = roleInfo;
    }
}
