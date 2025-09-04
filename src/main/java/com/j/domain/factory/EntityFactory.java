package com.j.domain.factory;

import com.j.domain.entity.BaseEntity;
import org.springframework.lang.Nullable;

import java.util.function.Consumer;

/**
 * 实体工厂 当实体属性比较多时直接new比较复杂 交由factory完成
 *
 * @author Jinx
 */
public interface EntityFactory<T extends BaseEntity> {

    /**
     * 创建entity
     *
     * @param source   源对象 entity的属性值从里面获取
     * @param callback 创建完成前回调  当无法通过source完成时通过该回调让调用方自己构建
     * @return 实体
     */
    T create(@Nullable Object source, Consumer<T> callback);

    default T create(Object source) {
        // @formatter:off
        return create(source, entity -> {});
        // @formatter:on
    }

    default T create() {
        return create(null);
    }
}
