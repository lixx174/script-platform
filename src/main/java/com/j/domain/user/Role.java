package com.j.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Jinx
 */
@Getter
@Setter
@Entity
@Table(name = "tb_role")
public class Role {

    @Id
    private Long id;

    private String name;

    private String remark;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Authority> authorities;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<User> users;

    public Role(String name, String remark) {
        this.name = name;
        this.remark = remark;
    }

    public Collection<String> getPermissions() {
        return authorities.stream()
                .map(Authority::getPermission)
                .filter(StringUtils::hasText)
                .collect(Collectors.toSet());
    }
}
