package com.ethann.springshop.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class OrderNumberGenerator {

  public String generateOrderNumber() {
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    String randomSuffix = String.format("%04d", new Random().nextInt(10000));
    return "ORD-" + timestamp + "-" + randomSuffix;
  }
}