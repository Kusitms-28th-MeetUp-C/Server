package com.kusitms.mainservice.global.config.security.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenInfo {
    private String token;
    private Long expireTime;
}
