package com.ethann.springshop.controller;

import com.ethann.springshop.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
  private final InventoryService inventoryService;

  @GetMapping("/{skuCode}")
  @ResponseStatus(HttpStatus.OK)
  public boolean isInStock(@PathVariable String skuCode, @RequestParam Integer quantity) {
    return inventoryService.isInStock(skuCode, quantity);
  }
}