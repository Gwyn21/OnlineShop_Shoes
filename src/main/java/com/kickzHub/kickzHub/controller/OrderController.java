package com.kickzHub.kickzHub.controller;

import com.kickzHub.kickzHub.dto.OrderItemDTO;
import com.kickzHub.kickzHub.model.Order;
import com.kickzHub.kickzHub.model.OrderItem;
import com.kickzHub.kickzHub.request.OrderRequestDTO;
import com.kickzHub.kickzHub.request.OrderStatusUpdateDTO;
import com.kickzHub.kickzHub.response.OrderResponseDTO;
import com.kickzHub.kickzHub.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        OrderResponseDTO response = convertToResponseDTO(order);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.ok(convertToResponseDTO(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponseDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatusUpdateDTO statusUpdate) {
        Order updatedOrder = orderService.updateOrderStatus(id, statusUpdate.getStatus());
        if (updatedOrder != null) {
            OrderResponseDTO response = convertToResponseDTO(updatedOrder);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    private OrderResponseDTO convertToResponseDTO(Order order) {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setId(order.getId());
        responseDTO.setUserId(order.getUser().getId());
        responseDTO.setItems(order.getItems().stream().map(this::convertToOrderItemDTO).collect(Collectors.toList()));
        responseDTO.setTotalAmount(order.getTotalAmount());
        responseDTO.setStatus(order.getStatus());
        responseDTO.setDescription(order.getDescription());
        responseDTO.setShippingAddressId(order.getShippingAddress().getId());
        responseDTO.setPaymentMethod(order.getPaymentMethod());
        responseDTO.setCreatedAt(order.getCreatedAt());
        return responseDTO;
    }

    private OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setProductId(orderItem.getProduct().getId());
        itemDTO.setQuantity(orderItem.getQuantity());
        return itemDTO;
    }
}
