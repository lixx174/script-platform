package com.j.domain.entity.user;

import com.j.domain.entity.BaseEntity;
import com.j.domain.primitive.AuthorityType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Jinx
 */
@Getter
@Setter
@Entity
@Table(name = "tb_authority")
public class Authority extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private AuthorityType type;

    private String path;

    private String permission;

    private Integer sort;

    @ManyToMany(mappedBy = "authorities")
    private Collection<Role> roles;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Authority parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private Collection<Authority> children;
}
