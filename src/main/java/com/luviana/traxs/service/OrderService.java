package com.luviana.traxs.service;

import com.luviana.traxs.dto.OrderResponse;
import com.luviana.traxs.dto.UpdateOrderRequest;
import com.luviana.traxs.enums.OrderStatus;
import com.luviana.traxs.helper.SortValidator;
import com.luviana.traxs.model.Order;
import com.luviana.traxs.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Page<OrderResponse> getOrders(
            int page,
            int size,
            String sortBy,
            String direction,
            OrderStatus status,
            String email
    ) {

        if (!SortValidator.isValidSort(sortBy)) {
            sortBy = "orderDate";
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Order> orders;

        if (status != null && email != null) {
            orders = repository.findByIsDeletedFalseAndStatusAndCustomerEmailContainingIgnoreCase(
                    status, email, pageable
            );

        } else if (status != null) {
            orders = repository.findByIsDeletedFalseAndStatus(status, pageable);

        } else if (email != null) {
            orders = repository.findByIsDeletedFalseAndCustomerEmailContainingIgnoreCase(email, pageable);

        } else {
            orders = repository.findByIsDeletedFalse(pageable);
        }

        return orders.map(OrderResponse::new);
    }

    @Transactional
    public OrderResponse getOrderByOrderId(String orderId) {

        Order order = repository.findByOrderIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with orderId: " + orderId));

        return new OrderResponse(order);
    }

    @Transactional
    public OrderResponse updateOrder(String orderId, UpdateOrderRequest request) {

        Order order = repository.findByOrderIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // ✅ only update if not null
        if (request.getCustomerName() != null) {
            order.setCustomerName(request.getCustomerName());
        }

        if (request.getCustomerEmail() != null) {
            order.setCustomerEmail(request.getCustomerEmail());
        }

        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }

        if (request.getAgentDate() != null) {
            order.setAgentDate(request.getAgentDate());
        }

        if (request.getFinishDate() != null) {
            order.setFinishDate(request.getFinishDate());
        }

        return new OrderResponse(order);
    }

    @Transactional
    public void deleteOrder(String orderId) {

        Order order = repository.findByOrderIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setIsDeleted(true);
    }
}
