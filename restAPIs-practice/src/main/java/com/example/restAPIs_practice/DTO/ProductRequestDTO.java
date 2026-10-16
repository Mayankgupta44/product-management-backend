package com.example.restAPIs_practice.DTO;

import com.example.restAPIs_practice.Entity.User;
import com.example.restAPIs_practice.Validator.ValidPrice;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 50, message = "Product name must be between 3 and 50 characters")
//    @Pattern(regexp = "^[A-Za-z]+$", message = "Product name must contain only letters")
    private String name;

    @NotNull(message = "Price is required")
    private Double price;

    private User user;

    private Integer categoryId;
}
