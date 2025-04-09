package com.ethann.springshop.service;

import com.ethann.springshop.dto.ProductRequest;
import com.ethann.springshop.dto.ProductResponse;
import com.ethann.springshop.exception.ResourceNotFoundException;
import com.ethann.springshop.model.Product;
import com.ethann.springshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
  private final ProductRepository productRepository;

  @Transactional
  public ProductResponse createProduct(ProductRequest productRequest) {
    log.info("Creating new product with SKU: {}", productRequest.getSkuCode());

    Product product = Product.builder()
        .id(UUID.randomUUID().toString())
        .name(productRequest.getName())
        .description(productRequest.getDescription())
        .skuCode(productRequest.getSkuCode())
        .price(productRequest.getPrice())
        .build();

    Product savedProduct = productRepository.save(product);
    log.info("Product created successfully with ID: {}", savedProduct.getId());

    return mapToProductResponse(savedProduct);
  }

  @Transactional(readOnly = true)
  public List<ProductResponse> getAllProducts() {
    log.info("Fetching all products");
    return productRepository.findAll().stream()
        .map(this::mapToProductResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public ProductResponse getProductBySkuCode(String skuCode) {
    log.info("Fetching product with SKU: {}", skuCode);
    Product product = productRepository.findBySkuCode(skuCode)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with SKU: " + skuCode));
    return mapToProductResponse(product);
  }

  private ProductResponse mapToProductResponse(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .skuCode(product.getSkuCode())
        .price(product.getPrice())
        .build();
  }
}