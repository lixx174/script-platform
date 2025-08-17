package com.j.domain.primitive;

/**
 * @author Jinx
 */
public enum OrderStatus {

    /**
     * 待支付
     */
    PENDING,
    /**
     * 已支付
     */
    PAID,
    /**
     * 已发货
     */
    SHIPPED,
    /**
     * 已完成
     */
    COMPLETED,
    /**
     * 已取消
     */
    CANCELLED,
    /**
     * 退货中
     */
    RETURNING,
    /**
     * 已退货
     */
    RETURNED
}
