package com.luviana.traxs.controller;

import com.luviana.traxs.dto.ApiResponse;
import com.luviana.traxs.dto.OrderResponse;
import com.luviana.traxs.dto.UpdateOrderRequest;
import com.luviana.traxs.enums.OrderStatus;
import com.luviana.traxs.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public Page<OrderResponse> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String email
    ) {

        return service.getOrders(page, size, sortBy, direction, status, email);
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable String id) {
        return ApiResponse.success(service.getOrderByOrderId(id));
    }

    @PatchMapping("/{orderId}")
    public ApiResponse<OrderResponse> updateOrder(
            @PathVariable String orderId,
            @RequestBody UpdateOrderRequest request
    ) {
        return ApiResponse.success(service.updateOrder(orderId, request));
    }

    @DeleteMapping("/{orderId}")
    public ApiResponse<String> deleteOrder(@PathVariable String orderId) {
        service.deleteOrder(orderId);
        return ApiResponse.success("Order deleted successfully");
    }
}
