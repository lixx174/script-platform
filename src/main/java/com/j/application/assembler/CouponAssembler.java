package com.j.application.assembler;

import com.qh.application.model.CouponDto;
import com.qh.domain.UserCoupon;
import com.qh.domain.primitive.CouponQuantity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author Jinx
 */
@Mapper
public interface CouponAssembler {

    @Mappings({
            @Mapping(target = "id", source = "coupon.id.id"),
            @Mapping(target = "name", source = "coupon.name"),
            @Mapping(target = "type", source = "coupon.type"),
            @Mapping(target = "discount", source = "coupon.discount"),
            @Mapping(target = "amount", source = "coupon.amount"),
            @Mapping(target = "minimumAmount", source = "coupon.minimumAmount"),
            @Mapping(target = "startTime", source = "coupon.period.startTime"),
            @Mapping(target = "endTime", source = "coupon.period.endTime"),
            @Mapping(target = "remark", source = "coupon.remark"),
            @Mapping(target = "count", source = "couponQuantity", qualifiedByName = "assembleCount"),
    })
    CouponDto assemble(UserCoupon uc);

    List<CouponDto> assemble(List<UserCoupon> ucs);


    @Named("assembleCount")
    default Integer assembleCount(CouponQuantity cq) {
        return cq.getUsableCount();
    }
}
