package com.ethann.springshop.service;

import com.ethann.springshop.exception.ResourceNotFoundException;
import com.ethann.springshop.model.Inventory;
import com.ethann.springshop.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
  private final InventoryRepository inventoryRepository;

  @Transactional(readOnly = true)
  public boolean isInStock(String skuCode, Integer quantityRequired) {
    log.info("Checking if product with SKU: {} is in stock with quantity: {}", skuCode, quantityRequired);
    return inventoryRepository.findBySkuCode(skuCode)
        .map(inventory -> inventory.getQuantity() >= quantityRequired)
        .orElse(false);
  }

  @Transactional
  public void updateStock(String skuCode, Integer quantityToReduce) {
    log.info("Updating stock for product with SKU: {}, reducing by: {}", skuCode, quantityToReduce);
    Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
        .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for SKU: " + skuCode));

    inventory.setQuantity(inventory.getQuantity() - quantityToReduce);
    inventoryRepository.save(inventory);
    log.info("Stock updated successfully for SKU: {}, new quantity: {}", skuCode, inventory.getQuantity());
  }
}
