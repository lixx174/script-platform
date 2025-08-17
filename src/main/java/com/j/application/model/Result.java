package com.j.application.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 统一响应模型
 *
 * @author Jinx
 **/
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T> {
    /**
     * 响应code
     */
    private final int code;
    /**
     * 提示信息
     */
    private final String msg;
    /**
     * 响应数据
     */
    private final T data;

    public static <T> Result<T> succeed() {
        return succeed((T) null);
    }

    public static <T> Result<T> succeed(Runnable task) {
        task.run();
        return succeed();
    }

    public static <T> Result<T> succeed(T data) {
        return of(200, "success", data);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return of(code, msg, null);
    }

    public static <T> Result<T> of(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }
}
