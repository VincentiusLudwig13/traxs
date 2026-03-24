package com.luviana.traxs.dto;

import com.luviana.traxs.enums.OrderStatus;
import com.luviana.traxs.model.Order;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class OrderResponse {

    private String orderId;
    private String customerName;
    private String customerEmail;
    private OrderStatus status;
    private LocalDate orderDate;
    private LocalDate agentDate;
    private LocalDate finishDate;

    public OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.customerName = order.getCustomerName();
        this.customerEmail = order.getCustomerEmail();
        this.status = order.getStatus();
        this.orderDate = order.getOrderDate();
        this.agentDate = order.getAgentDate();
        this.finishDate = order.getFinishDate();
    }
}
