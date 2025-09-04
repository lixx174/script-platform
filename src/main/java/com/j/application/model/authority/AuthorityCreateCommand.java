package com.j.application.model.authority;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j.domain.primitive.AuthorityType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author Jinx
 */
@Getter
@Setter
public class AuthorityCreateCommand {

    /**
     * 父id
     */
    private Long pid;

    private String name;

    /**
     * 权限类型
     */
    private AuthorityType type;

    /**
     * 路由路径
     */
    private String path;

    private String permission;

    /**
     * 排序号
     */
    private Integer sort;

    public AuthorityCreateCommand(@JsonProperty("pid") Long pid,
                                  @JsonProperty("name") String name,
                                  @JsonProperty("type") AuthorityType type,
                                  @JsonProperty("path") String path,
                                  @JsonProperty("permission") String permission,
                                  @JsonProperty("sort") Integer sort) {
        Assert.hasText(name, "权限名称不能为空");
        Assert.hasText(permission, "权限标识不能为空");
        if (type == AuthorityType.ROUTE) {
            Assert.hasText(path, "路由路径不能为空");
        }
        if (Objects.nonNull(sort)) {
            Assert.isTrue(sort > 0, "排序号需大于0");
        }

        this.pid = pid;
        this.name = name;
        this.type = type;
        this.path = path;
        this.permission = permission;
        this.sort = sort;
    }
}
