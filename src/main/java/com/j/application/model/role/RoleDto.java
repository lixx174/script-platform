package com.j.application.model.role;

import com.j.application.model.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jinx
 */
@Getter
@Setter
public class RoleDto extends BaseDto {

    /**
     * 名字
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
}
