package com.luviana.traxs.test;

import com.luviana.traxs.dto.UpdateOrderRequest;
import com.luviana.traxs.model.Order;
import com.luviana.traxs.repository.OrderRepository;
import com.luviana.traxs.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraxsTest {

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderService service;

    @Test
    void getOrderByOrderId_success() {
        Order order = new Order();
        order.setOrderId("ORD-002");

        when(repository.findByOrderIdAndIsDeletedFalse("ORD-002"))
                .thenReturn(Optional.of(order));

        var result = service.getOrderByOrderId("ORD-002");

        assertNotNull(result);
        assertEquals("ORD-002", result.getOrderId());
    }

    @Test
    void getOrderByOrderId_notFound() {
        when(repository.findByOrderIdAndIsDeletedFalse("ORD-999"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.getOrderByOrderId("ORD-999");
        });
    }

    @Test
    void updateOrder_success() {
        Order order = new Order();
        order.setOrderId("ORD-002");

        when(repository.findByOrderIdAndIsDeletedFalse("ORD-002"))
                .thenReturn(Optional.of(order));

        UpdateOrderRequest request = new UpdateOrderRequest();
        request.setCustomerName("Updated");

        var result = service.updateOrder("ORD-002", request);

        assertEquals("Updated", result.getCustomerName());
    }

    @Test
    void updateOrder_notFound() {
        when(repository.findByOrderIdAndIsDeletedFalse("ORD-999"))
                .thenReturn(Optional.empty());

        UpdateOrderRequest request = new UpdateOrderRequest();

        assertThrows(RuntimeException.class, () -> {
            service.updateOrder("ORD-999", request);
        });
    }

    @Test
    void deleteOrder_success() {
        Order order = new Order();
        order.setOrderId("ORD-003");
        order.setIsDeleted(false);

        when(repository.findByOrderIdAndIsDeletedFalse("ORD-003"))
                .thenReturn(Optional.of(order));

        service.deleteOrder("ORD-003");

        assertTrue(order.getIsDeleted());
    }

    @Test
    void deleteOrder_notFound() {
        when(repository.findByOrderIdAndIsDeletedFalse("ORD-999"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.deleteOrder("ORD-999");
        });
    }
}
