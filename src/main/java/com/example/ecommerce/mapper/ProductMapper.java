package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDto productDto);
}
