package com.j.application.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.function.Function;

/**
 * 分页响应模型
 *
 * @author jinx
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PageReply<T> {

    /**
     * 当前页码
     */
    private final Number current;
    /**
     * 分页大小
     */
    private final Number size;
    /**
     * 总页数
     */
    private final Number pages;
    /**
     * 数据
     */
    private final Collection<T> records;

    public static <T> PageReply<T> of(Number current, Number size, Number pages, Collection<T> records) {
        return new PageReply<>(current, size, pages, records);
    }

    /**
     * jpa 分页参数构建为系统分页模型
     *
     * @param page      jpa分页模型
     * @param converter 数据转换器
     * @param <S>       源分页数据类型  一般为领域内模型
     * @param <T>       响应数据类型  一般为dto
     * @return 系统分页模型
     */
    public static <S, T> PageReply<T> of(Page<S> page, Function<Collection<S>, Collection<T>> converter) {
        return of(page.getNumber(), page.getSize(), page.getTotalPages(), converter.apply(page.getContent()));
    }
}
