package com.ethann.springshop.repository;

import com.ethann.springshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
  Optional<Product> findBySkuCode(String skuCode);
}