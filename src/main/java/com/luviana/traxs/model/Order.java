package com.luviana.traxs.model;

import com.luviana.traxs.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "trx_orders", indexes = {
        @Index(name = "idx_order_status", columnList = "status"),
        @Index(name = "idx_customer_email", columnList = "customerEmail"),
        @Index(name = "idx_order_date", columnList = "orderDate")
})
public class Order {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    private LocalDate orderDate;
    private LocalDate agentDate;
    private LocalDate finishDate;

    // Soft delete (recommended)
    @Column(nullable = false)
    private Boolean isDeleted = false;

    // Audit fields (optional but recommended)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.orderDate = this.orderDate != null ? this.orderDate : LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
