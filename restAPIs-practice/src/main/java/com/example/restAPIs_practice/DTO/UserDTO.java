package com.example.restAPIs_practice.DTO;

import com.example.restAPIs_practice.Entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 50)
    @Pattern(
            regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$",
            message = "Name must contain letters only, with single spaces between names"
    )
    private String name;

    private String password;

    private String role;

    @NotBlank
    @Email
    private String email;

    @JsonIgnore
    private List<Product> products;

}
