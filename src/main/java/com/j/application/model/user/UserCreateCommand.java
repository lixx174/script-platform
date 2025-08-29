package com.j.application.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Jinx
 */
@Getter
@Setter
public class UserCreateCommand {

    @NotEmpty(message = "姓名不能为空")
    @Size(max = 20, message = "姓名长度不能超过20")
    private String name;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 8, max = 20, message = "用户名长度在8到20之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 50, message = "密码长度在6到50之间")
    private String password;

    @NotEmpty(message = "角色不能为空")
    private Collection<Long> roleIds;
}
