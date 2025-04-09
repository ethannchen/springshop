package com.ethann.springshop.dto;

import com.ethann.springshop.model.Order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
  private String orderNumber;
  private String skuCode;
  private BigDecimal price;
  private Integer quantity;
  private BigDecimal totalAmount;
  private LocalDateTime orderDate;
  private OrderStatus status;
}