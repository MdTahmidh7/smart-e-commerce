package com.example.ecommerce.util;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        if (productService.getAllProducts(org.springframework.data.domain.Pageable.unpaged()).getTotalElements() == 0) {
            for (int i = 1; i <= 20; i++) {
                ProductDto productDto = new ProductDto();
                productDto.setProductName("Product " + i);
                productDto.setDescription("Description for product " + i);
                productDto.setPrice(10.0 * i);
                productDto.setQuantity(100);
                productDto.setImageUrl("http://example.com/product" + i + ".jpg");
                productService.createProduct(productDto);
            }
            System.out.println("Database seeded with 20 products.");
        }
    }
}
