package com.example.jwt.domain.member;

import lombok.Getter;

import javax.persistence.*;

/**
 * @author Alexander
 * @date 2020-10-20
 **/
@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
