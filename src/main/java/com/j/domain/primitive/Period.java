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

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public boolean isBetween(LocalDateTime ldt) {
        if (ldt == null) {
            return false;
        }

        return ldt.isAfter(startTime) && ldt.isBefore(endTime);
    }
}
