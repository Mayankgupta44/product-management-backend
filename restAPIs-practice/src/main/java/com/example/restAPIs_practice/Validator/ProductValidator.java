package com.example.restAPIs_practice.Validator;

import com.example.restAPIs_practice.DTO.ProductRequestDTO;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("productValidator")
public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ProductRequestDTO product = (ProductRequestDTO) target;

        if (product.getPrice() == null || product.getPrice() <= 0) {
            errors.rejectValue(
                    "price",
                    "invalid.price",
                    "Price must be greater than 0 (ProductValidator class)"
            );
        }

    }
}