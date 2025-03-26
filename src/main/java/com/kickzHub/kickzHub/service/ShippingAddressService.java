package com.kickzHub.kickzHub.service;

import com.kickzHub.kickzHub.exception.ShippingAddressException;
import com.kickzHub.kickzHub.model.Order;
import com.kickzHub.kickzHub.model.ShippingAddress;
import com.kickzHub.kickzHub.repository.OrderRepository;
import com.kickzHub.kickzHub.repository.ShippingAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingAddressService {
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private OrderRepository orderRepository;

    public ShippingAddress addShippingAddress(ShippingAddress address) {
        return shippingAddressRepository.save(address);
    }

    public List<ShippingAddress> getShippingAddressesByUserId(Long userId) {
        return shippingAddressRepository.findByUserId(userId);
    }

    public void deleteShippingAddress(Long id) {
        List<Order> orders = orderRepository.findByShippingAddressId(id);
        if (!orders.isEmpty()) {
            throw new ShippingAddressException("Cannot delete shipping address as it is referenced by existing orders.");
        }
        shippingAddressRepository.deleteById(id);
    }

    public List<ShippingAddress> getAllShippingAddresses() {
        return shippingAddressRepository.findAll();
    }
}
