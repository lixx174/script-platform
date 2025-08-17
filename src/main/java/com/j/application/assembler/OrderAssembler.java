package com.j.application.assembler;

import com.qh.application.model.OrderDto;
import com.qh.domain.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author Jinx
 */
@Mapper
public interface OrderAssembler {

    @Mappings({
            @Mapping(target = "id", source = "id.id"),
            @Mapping(target = "number", source = "number.value"),
            @Mapping(target = "userId", source = "userId.id"),
            @Mapping(target = "paymentStatus", source = "payment.status"),
            @Mapping(target = "paymentMethod", source = "payment.method"),
    })
    OrderDto assemble(Order order);

    List<OrderDto> assemble(List<Order> orders);
}
