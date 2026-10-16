package com.example.restAPIs_practice.Mapper;

import com.example.restAPIs_practice.DTO.CategoryDTO;
import com.example.restAPIs_practice.Entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDTO dto) {

        Category category = new Category();

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        return category;
    }

    public CategoryDTO toResponseDTO(Category category) {

        CategoryDTO dto = new CategoryDTO();

        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        return dto;
    }
}