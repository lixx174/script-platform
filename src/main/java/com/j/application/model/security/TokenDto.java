package com.j.application.model.security;

import com.j.application.model.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

/**
 * @author Jinx
 */
@Getter
@Setter
public class TokenDto extends BaseDto {

    private String type;

    private String accessToken;

    private String refreshToken;

    private Duration accessExpireIn;

    private Duration refreshExpireIn;
}
