package com.example.restAPIs_practice.Service;

import com.example.restAPIs_practice.DTO.ProductResponseDTO;
import com.example.restAPIs_practice.DTO.UserDTO;
import com.example.restAPIs_practice.Entity.Product;
import com.example.restAPIs_practice.Entity.User;
import com.example.restAPIs_practice.Exception.UserNotFoundException;
import com.example.restAPIs_practice.Mapper.UserMapper;
import com.example.restAPIs_practice.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public  UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getAllUsers(){
        List<User> list = userRepository.findAll();
        List<UserDTO> response = new ArrayList<>();
        for (User p : list){
            response.add(userMapper.toResponse(p));
        }
        return response;
    }

    public UserDTO getUserById (Integer id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return userMapper.toResponse(user);
    }

    public UserDTO createUser(UserDTO dto){
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already registered"
            );
        }

        User user = userMapper.toEntity(dto);

        user.setId(null);
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setProducts(null);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    public UserDTO updateUser(Integer id, UserDTO dto){
        User user = userMapper.toEntity(dto);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());

        User savedUser = userRepository.save(existingUser);
        return userMapper.toResponse(savedUser);
    }

    public void deleteUser(Integer id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.deleteById(id);
    }

    public UserDTO updateUserWithProduct(UserDTO dto){
        User user = userMapper.toEntity(dto);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
}
