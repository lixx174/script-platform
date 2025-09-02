package com.j.application.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Jinx
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseDto {

    private Long id;

    private LocalDateTime createAt;

    private String createBy;

    private LocalDateTime modifyAt;

    private String modifyBy;
}
