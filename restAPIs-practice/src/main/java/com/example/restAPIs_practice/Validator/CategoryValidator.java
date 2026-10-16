package com.example.restAPIs_practice.Validator;

import com.example.restAPIs_practice.DTO.CategoryDTO;
import com.example.restAPIs_practice.Repository.CategoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CategoryValidator implements Validator {

    private final CategoryRepository categoryRepository;

    public CategoryValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CategoryDTO category = (CategoryDTO) target;

        if (category.getName() == null || category.getName().trim().isEmpty()) {
            errors.rejectValue(
                    "name",
                    "category.name.required",
                    "Category name is required"
            );
        }

        if (category.getName() != null) {
            String name = category.getName().trim();

            boolean duplicate;

            if (category.getId() == null) {
                duplicate = categoryRepository.existsByName(name);
            } else {
                duplicate = categoryRepository.existsByNameAndIdNot(
                        name,
                        category.getId()
                );
            }

            if (duplicate) {
                errors.rejectValue(
                        "name",
                        "category.name.duplicate",
                        "Category already exists"
                );
            }
        }
    }
}