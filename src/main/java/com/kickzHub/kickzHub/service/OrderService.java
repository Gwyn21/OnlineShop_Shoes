package com.kickzHub.kickzHub.service;

import com.kickzHub.kickzHub.dto.OrderItemDTO;
import com.kickzHub.kickzHub.model.Product;
import com.kickzHub.kickzHub.model.Order;
import com.kickzHub.kickzHub.model.OrderItem;
import com.kickzHub.kickzHub.model.ShippingAddress;
import com.kickzHub.kickzHub.model.User;
import com.kickzHub.kickzHub.repository.ProductRepository;
import com.kickzHub.kickzHub.repository.OrderRepository;
import com.kickzHub.kickzHub.repository.ShippingAddressRepository;
import com.kickzHub.kickzHub.repository.UserRepository;
import com.kickzHub.kickzHub.request.OrderRequestDTO;
import com.kickzHub.kickzHub.response.OrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(OrderRequestDTO orderRequest) {
        Order order = new Order();
        
        // Set user
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);
        
        // Set shipping address
        ShippingAddress shippingAddress = shippingAddressRepository.findById(orderRequest.getShippingAddressId())
                .orElseThrow(() -> new RuntimeException("Shipping address not found"));
        order.setShippingAddress(shippingAddress);
        
        // Set order items
        List<OrderItem> orderItems = orderRequest.getItems().stream().map(itemDTO -> {
            OrderItem orderItem = new OrderItem();
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order);
            
            // Decrement stock quantity
            int newStockQuantity = product.getStockQuantity() - itemDTO.getQuantity();
            if (newStockQuantity < 0) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }
            product.setStockQuantity(newStockQuantity);
            productRepository.save(product);
            
            return orderItem;
        }).collect(Collectors.toList());
        order.setItems(orderItems);
        
        // Set other fields
        order.setStatus(orderRequest.getStatus());
        order.setDescription(orderRequest.getDescription());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setCreatedAt(LocalDateTime.now());
        
        // Set total amount from user input instead of calculating it
        order.setTotalAmount(orderRequest.getTotalAmount());

        // Save order
        Order savedOrder = orderRepository.save(order);
        
        // Gửi email xác nhận đơn hàng
        sendOrderConfirmationEmail(user.getEmail(), savedOrder);
        
        return savedOrder;
    }

    private void sendOrderConfirmationEmail(String email, Order order) {
        String subject = "Xác nhận đơn hàng";
        String content = "<p>Xin chào,</p>"
                + "<p>Đơn hàng của bạn đã được đặt thành công!</p>"
                + "<p>Thông tin đơn hàng:</p>"
                + "<p>ID đơn hàng: " + order.getId() + "</p>"
                + "<p>Tổng số tiền: " + order.getTotalAmount() + " VNĐ</p>"
                + "<p>Trạng thái: Đặt thành công</p>"
                + "<p>Cảm ơn bạn đã mua hàng!</p>";

        try {
            HtmlEmail htmlEmail = new HtmlEmail();
            htmlEmail.setHostName("smtp-relay.brevo.com");
            htmlEmail.setSmtpPort(587);
            htmlEmail.setStartTLSEnabled(true);
            htmlEmail.setAuthentication("h5studiogl@gmail.com", "fScdnZ4WmEDqjBA1");
            htmlEmail.setFrom("h5studiogl@gmail.com");
            htmlEmail.setSubject(subject);
            htmlEmail.setHtmlMsg(content);
            htmlEmail.addTo(email);
            htmlEmail.setCharset("UTF-8");

            htmlEmail.send();
            System.out.println("Email xác nhận đã được gửi đến " + email);
        } catch (EmailException e) {
            System.err.println("Lỗi khi gửi email xác nhận: " + e.getMessage());
        }
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
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

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void cancelOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            existingOrder.setStatus("rejected"); 
            orderRepository.save(existingOrder); 
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }

    public List<Product> getProductsByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .flatMap(order -> order.getItems().stream()
                        .map(OrderItem::getProduct))
                .distinct() // To avoid duplicates
                .collect(Collectors.toList());
    }
}
