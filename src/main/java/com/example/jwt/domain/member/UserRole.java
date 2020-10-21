package com.example.jwt.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Alexander
 * @date 2020-10-20
 **/
@Getter
@RequiredArgsConstructor
public enum UserRole {

    ADMIN("ROLE_ADMIN"),

    USER("ROLE_USER");

    private final String text;

}
