package com.j.domain.primitive;

import lombok.Getter;
import lombok.Setter;

/**
 * 优惠卷数量
 *
 * @author Jinx
 */
@Getter
@Setter
public class CouponQuantity {

    /**
     * 总数
     */
    private Integer totalCount;
    /**
     * 已经使用数量
     */
    private Integer usedCount;


    /**
     * 获取可用数量 - 读不用管数据是否可靠 由写来保证一致
     *
     * @return 可用优惠卷数量
     */
    public Integer getUsableCount() {
        return totalCount - usedCount;
    }
}
