package com.example.restAPIs_practice.Mapper;

import com.example.restAPIs_practice.DTO.ProductRequestDTO;
import com.example.restAPIs_practice.DTO.ProductResponseDTO;
import com.example.restAPIs_practice.DTO.UserDTO;
import com.example.restAPIs_practice.Entity.Product;
import com.example.restAPIs_practice.Entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
//    public User toEntity(UserDTO dto) {
//        User user = new User();
//
//        user.setName(dto.getName());
//        user.setPassword(dto.getPassword());
//        user.setRole(dto.getRole());
//        user.setEmail(dto.getEmail());
//        user.setProducts(dto.getProducts());
//
//        return user;
//    }
//
//    public UserDTO toResponse(User user) {
//        UserDTO dto = new UserDTO();
//
//        dto.setId(user.getId());
//        dto.setName(user.getName());
//        dto.setEmail(user.getEmail());
//        dto.setProducts(user.getProducts());
//        dto.setRole(user.getRole());
//
//        return dto;
//    }

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    public UserDTO toResponse(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
