package com.j.application.model.authority;

import com.j.application.model.BaseDto;
import com.j.domain.primitive.AuthorityType;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Jinx
 */
@Getter
@Setter
public class AuthorityDto extends BaseDto {

    private String name;

    private AuthorityType type;

    private String path;

    private String permission;

    private Integer sort;

    private Collection<AuthorityDto> children;
}
