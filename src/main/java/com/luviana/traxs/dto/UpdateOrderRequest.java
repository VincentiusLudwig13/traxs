package com.luviana.traxs.dto;

import com.luviana.traxs.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateOrderRequest {

    private String customerName;
    private String customerEmail;
    private OrderStatus status;
    private LocalDate agentDate;
    private LocalDate finishDate;
}
