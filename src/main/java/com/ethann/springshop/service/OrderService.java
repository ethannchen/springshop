package com.ethann.springshop.service;

import com.ethann.springshop.dto.OrderRequest;
import com.ethann.springshop.dto.OrderResponse;
import com.ethann.springshop.exception.ProductOutOfStockException;
import com.ethann.springshop.exception.ResourceNotFoundException;
import com.ethann.springshop.model.Order;
import com.ethann.springshop.model.Product;
import com.ethann.springshop.repository.OrderRepository;
import com.ethann.springshop.repository.ProductRepository;
import com.ethann.springshop.util.OrderNumberGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final InventoryService inventoryService;
  private final OrderNumberGenerator orderNumberGenerator;

  @Transactional
  public OrderResponse placeOrder(OrderRequest orderRequest) {
    log.info("Placing order for product with SKU: {}, quantity: {}",
        orderRequest.getSkuCode(), orderRequest.getQuantity());

    // Check if product exists
    Product product = productRepository.findBySkuCode(orderRequest.getSkuCode())
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with SKU: " + orderRequest.getSkuCode()));

    // Check if product is in stock
    boolean isInStock = inventoryService.isInStock(orderRequest.getSkuCode(), orderRequest.getQuantity());

    if (!isInStock) {
      throw new ProductOutOfStockException("Product with SKU: " + orderRequest.getSkuCode() + " is out of stock");
    }

    // Create and save order
    Order order = Order.builder()
        .orderNumber(orderNumberGenerator.generateOrderNumber())
        .skuCode(orderRequest.getSkuCode())
        .price(product.getPrice())
        .quantity(orderRequest.getQuantity())
        .orderDate(LocalDateTime.now())
        .status(Order.OrderStatus.CONFIRMED)
        .build();

    Order savedOrder = orderRepository.save(order);

    // Update inventory
    inventoryService.updateStock(orderRequest.getSkuCode(), orderRequest.getQuantity());

    log.info("Order placed successfully with order number: {}", savedOrder.getOrderNumber());

    return OrderResponse.builder()
        .orderNumber(savedOrder.getOrderNumber())
        .skuCode(savedOrder.getSkuCode())
        .price(savedOrder.getPrice())
        .quantity(savedOrder.getQuantity())
        .totalAmount(savedOrder.getPrice().multiply(new java.math.BigDecimal(savedOrder.getQuantity())))
        .orderDate(savedOrder.getOrderDate())
        .status(savedOrder.getStatus())
        .build();
  }
}