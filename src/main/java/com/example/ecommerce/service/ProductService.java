package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);

    Product getById(Long id);

    ProductDto createProduct(ProductDto productDto);
}
