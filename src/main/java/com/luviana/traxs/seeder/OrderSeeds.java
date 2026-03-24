package com.luviana.traxs.seeder;

import com.luviana.traxs.enums.OrderStatus;
import com.luviana.traxs.model.Order;
import com.luviana.traxs.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Profile("dev")
@Configuration
public class OrderSeeds {

    @Bean
    CommandLineRunner seedDatabase(OrderRepository orderRepository) {
        return args -> {

            // prevent duplicate seeding
            if (orderRepository.count() > 0) {
                return;
            }

            Order order1 = new Order();
            order1.setOrderId("ORD-001");
            order1.setCustomerName("John Doe");
            order1.setCustomerEmail("john@example.com");
            order1.setStatus(OrderStatus.PENDING);
            order1.setOrderDate(LocalDate.now());

            Order order2 = new Order();
            order2.setOrderId("ORD-002");
            order2.setCustomerName("Alice Smith");
            order2.setCustomerEmail("alice@example.com");
            order2.setStatus(OrderStatus.IN_PROGRESS);
            order2.setAgentDate(LocalDate.now());

            Order order3 = new Order();
            order3.setOrderId("ORD-003");
            order3.setCustomerName("Bob Lee");
            order3.setCustomerEmail("bob@example.com");
            order3.setStatus(OrderStatus.COMPLETED);
            order3.setFinishDate(LocalDate.now());

            orderRepository.saveAll(List.of(order1, order2, order3));

            System.out.println("✅ Trx_Order seeded!");
        };
    }
}
