package com.j.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jinx
 */
@Getter
@Setter
@Entity
@Table(name = "tb_authority")
public class Authority {

    @Id
    private Long id;

    private String permission;
}
