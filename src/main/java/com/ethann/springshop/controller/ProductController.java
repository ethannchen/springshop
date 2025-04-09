package com.ethann.springshop.controller;

import com.ethann.springshop.dto.ProductRequest;
import com.ethann.springshop.dto.ProductResponse;
import com.ethann.springshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
    return productService.createProduct(productRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponse> getAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/{skuCode}")
  @ResponseStatus(HttpStatus.OK)
  public ProductResponse getProductBySkuCode(@PathVariable String skuCode) {
    return productService.getProductBySkuCode(skuCode);
  }
}