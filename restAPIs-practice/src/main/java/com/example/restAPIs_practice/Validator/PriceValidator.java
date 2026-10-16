package com.example.restAPIs_practice.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceValidator implements ConstraintValidator<ValidPrice, Double> {
//  PriceValidator is a validator for the @ValidPrice annotation and it validates a Double value.
    @Override
    public boolean isValid(Double price, ConstraintValidatorContext context){
        return price != null && price > 0;
    }
}
