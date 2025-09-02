package com.j.domain.entity.user;

import com.j.domain.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@Table(name = "tb_role")
public class Role extends BaseEntity {

    private String name;

    private String remark;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_role_authority",
            joinColumns = {
                    @JoinColumn(name = "role_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "authority_id")
            }
    )
    private Collection<Authority> authorities;

    @ManyToMany(mappedBy = "roles")
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
