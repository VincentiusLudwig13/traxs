package com.luviana.traxs.repository;

import com.luviana.traxs.enums.OrderStatus;
import com.luviana.traxs.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByIsDeletedFalseAndStatusAndCustomerEmailContainingIgnoreCase(
            OrderStatus status,
            String email,
            Pageable pageable
    );

    Page<Order> findByIsDeletedFalse(
            Pageable pageable
    );

    Page<Order> findByIsDeletedFalseAndStatus(
            OrderStatus status,
            Pageable pageable
    );

    Page<Order> findByIsDeletedFalseAndCustomerEmailContainingIgnoreCase(
            String email,
            Pageable pageable
    );

    Optional<Order> findByOrderIdAndIsDeletedFalse(String orderId);
}
