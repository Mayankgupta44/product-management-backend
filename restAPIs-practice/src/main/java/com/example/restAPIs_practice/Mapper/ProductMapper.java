package com.example.restAPIs_practice.Mapper;

import com.example.restAPIs_practice.DTO.ProductRequestDTO;
import com.example.restAPIs_practice.DTO.ProductResponseDTO;
import com.example.restAPIs_practice.Entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toEntity(ProductRequestDTO dto){
        Product product = new Product();
        product.setPrice(dto.getPrice());
        product.setName(dto.getName());
//        product.setUser(dto.getUser());
        return product;
    }
//
//    public ProductResponseDTO toResponse(Product product){
//        ProductResponseDTO dto = new ProductResponseDTO();
//        dto.setId(product.getId());
//        dto.setName(product.getName());
//        dto.setPrice(product.getPrice());
//        dto.setUser(product.getUser());
//        return dto;
//    }

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

//    public Product toEntity(ProductRequestDTO dto) {
//        return modelMapper.map(dto, Product.class);
//    }

    public ProductResponseDTO toResponse(Product product) {
        return modelMapper.map(product, ProductResponseDTO.class);
    }
}
