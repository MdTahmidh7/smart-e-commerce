package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<Product> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productRepository.save(productMapper.toEntity(productDto));
        return productMapper.toDto(product);
    }

}
