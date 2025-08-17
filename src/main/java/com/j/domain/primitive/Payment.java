package com.j.domain.primitive;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jinx
 */
@Getter
@Setter
public class Payment {

    private Status status;
    private Method method;


    public enum Status {
        /**
         * 未支付
         */
        UNPAID,
        /**
         * 支付中
         */
        PENDING,
        /**
         * 已支付
         */
        PAID,
        /**
         * 支付失败
         */
        FAILED,
        /**
         * 已退款
         */
        REFUNDED;
    }

    public enum Method {
        /**
         * 微信支付
         */
        WECHAT_PAY,
        /**
         * 支付宝支付
         */
        ALIPAY,
        /**
         * 信用卡
         */
        CREDIT_CARD
    }
}
