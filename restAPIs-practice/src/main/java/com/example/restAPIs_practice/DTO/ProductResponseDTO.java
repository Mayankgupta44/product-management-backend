package com.example.restAPIs_practice.DTO;

import com.example.restAPIs_practice.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDTO {

    private Integer id;
    private String name;
    private Double price;
    @JsonIgnore
    private User user;
    private Integer categoryId;
}
