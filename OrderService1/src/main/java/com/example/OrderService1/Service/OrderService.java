package com.example.OrderService1.Service;

import com.example.OrderService1.DTO.Inventory;
import com.example.OrderService1.DTO.OrderRequest;
import com.example.OrderService1.DTO.Product;
import com.example.OrderService1.Entity.Order;
import com.example.OrderService1.Exception.ResourceNotFoundException;
import com.example.OrderService1.Repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    private final String PRODUCT_SERVICE_URL = "http://localhost:8085/api/products";
    private final String INVENTORY_SERVICE_URL = "http://localhost:8086/inventory";

    public String placeOrder(OrderRequest orderRequest) {
        // Fetch product details
        Product product = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/" + orderRequest.getProductId(), Product.class);
        if (product == null || !product.getAvailability()) {
            throw new ResourceNotFoundException("Product not available or does not exist");
        }

        // Fetch inventory details
        Inventory inventory = restTemplate.getForObject(INVENTORY_SERVICE_URL + "/" + orderRequest.getProductId(), Inventory.class);
        if (inventory == null || inventory.getStockLevel() < orderRequest.getQuantity()) {
            throw new ResourceNotFoundException("Insufficient stock for product ID: " + orderRequest.getProductId());
        }

        // Update inventory stock
        inventory.setStockLevel(inventory.getStockLevel() - orderRequest.getQuantity());
        restTemplate.put(INVENTORY_SERVICE_URL + "/" + orderRequest.getProductId(), inventory);

        // Save the order
        Order order = new Order();
        order.setProductId(orderRequest.getProductId());
        order.setCustomerId(orderRequest.getCustomerId());
        order.setQuantity(orderRequest.getQuantity());
        order.setStatus("COMPLETED");
        orderRepository.save(order);

        return "Order placed successfully for Product ID: " + orderRequest.getProductId();
    }


    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
