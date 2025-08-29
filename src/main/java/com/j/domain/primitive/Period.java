package com.j.domain.primitive;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 时间段
 *
 * @author Jinx
 */
@Getter
@AllArgsConstructor
public class Period {

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    public boolean isWithin(LocalDateTime time) {
        return time != null && time.isAfter(startAt) && time.isBefore(endAt);
    }
}
