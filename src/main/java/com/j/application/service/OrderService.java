package com.j.application.service;

import com.qh.application.assembler.OrderAssembler;
import com.qh.application.model.OrderDto;
import com.qh.application.model.PageReply;
import com.qh.application.model.command.OrderLaunchCommand;
import com.qh.application.model.query.OrderPageQuery;
import com.qh.domain.client.PaymentClient;
import com.qh.domain.client.WechatJsApiPayment;
import com.qh.domain.order.Order;
import com.qh.domain.order.OrderRepository;
import com.qh.domain.repository.page.PageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Jinx
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderAssembler assembler;
    private final PaymentClient paymentClient;
    private final OrderRepository orderRepo;


    public String prepareLaunch() {
        return null;
    }

    public void launch(OrderLaunchCommand command) {
        System.out.println("command = " + command);

        paymentClient.pay();
        WechatJsApiPayment wechatJsApiPayment = paymentClient.wechatPay();

        System.out.println("pay successfully...");
        System.out.println(wechatJsApiPayment);
    }

    public PageReply<OrderDto> page(OrderPageQuery query) {
        PageImpl<Order> page = orderRepo.findAll(query);

        return PageReply.of(
                query.getCurrent(),
                query.getSize(),
                page.getPages(),
                assembler.assemble(page.getContents())
        );
    }
}
