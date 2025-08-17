package com.j.domain.primitive;

/**
 * 优惠卷类型
 *
 * @author Jinx
 */
public enum Coupons {

    /**
     * 折扣券：按百分比提供折扣，如10%折扣。
     */
    DISCOUNT,
    /**
     * 满减券：当订单金额达到一定值时减免特定金额，如满100减10。
     */
    FULL_DISCOUNT,
    /**
     * 现金券：直接抵扣固定金额，如50元现金券。
     */
    CASH,
    /**
     * 限时优惠券：在特定时间内有效的优惠券。
     */
    LIMITED_TIME;
}
