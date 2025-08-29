package com.j.application.model.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jinx
 */
@Getter
@Setter
public class RoleCreateCommand {

    @NotEmpty(message = "角色名不能为空")
    @Size(max = 10, message = "角色名不能超过10")
    private String name;

    @NotBlank(message = "角色备注不能为空")
    @Size(min = 1, max = 50, message = "角色备注长度在8到20之间")
    private String remark;
}
