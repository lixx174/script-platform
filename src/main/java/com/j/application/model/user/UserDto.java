package com.j.application.model.user;

import com.j.application.model.BaseDto;
import com.j.application.model.authority.AuthorityDto;
import com.j.domain.primitive.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Jinx
 */
@Getter
@Setter
public class UserDto extends BaseDto {

    private String name;

    private Status status;

    private Collection<AuthorityDto> authorities;
}
