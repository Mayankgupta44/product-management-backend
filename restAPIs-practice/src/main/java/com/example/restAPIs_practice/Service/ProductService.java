package com.example.restAPIs_practice.Service;

import com.example.restAPIs_practice.DTO.ProductRequestDTO;
import com.example.restAPIs_practice.DTO.ProductResponseDTO;
import com.example.restAPIs_practice.Entity.Category;
import com.example.restAPIs_practice.Entity.Product;
import com.example.restAPIs_practice.Entity.User;
import com.example.restAPIs_practice.Exception.CategoryNotFoundException;
import com.example.restAPIs_practice.Exception.ProductNotFoundException;
import com.example.restAPIs_practice.Exception.UserNotFoundException;
import com.example.restAPIs_practice.Mapper.ProductMapper;
import com.example.restAPIs_practice.Repository.CategoryRepository;
import com.example.restAPIs_practice.Repository.ProductRepository;
import com.example.restAPIs_practice.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository, ProductMapper productMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductResponseDTO> findAllProducts(String email) {
        User user = getUserByEmail(email);

        List<Product> products =
                productRepository.findByUser_Id(user.getId());

        List<ProductResponseDTO> response = new ArrayList<>();

        for (Product product : products) {
            response.add(productMapper.toResponse(product));
        }

        return response;
    }

    public ProductResponseDTO getProductById(int id, String email) {
        Product product = getOwnedProduct(id, email);
        return productMapper.toResponse(product);
    }

//    public ProductResponseDTO createProductWithUser(Integer userId,ProductRequestDTO productRequestDTO) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
//
//        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
//                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + productRequestDTO.getCategoryId()));
//
//        log.info("Create product with userId: {}", userId);
//
//        Product product = productMapper.toEntity(productRequestDTO);
//
//        product.setUser(user);
//        product.setCategory(category);
//
//        Product savedProduct = productRepository.save(product);
//
//        log.info("Product created");
//
//        return productMapper.toResponse(savedProduct);
//    }

    public ProductResponseDTO createProduct(String email, ProductRequestDTO requestDTO) {
        User user = getUserByEmail(email);

        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + requestDTO.getCategoryId()));

        Product product = productMapper.toEntity(requestDTO);

        product.setId(null);
        product.setUser(user);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    private Product getOwnedProduct(int id, String email) {
        User user = getUserByEmail(email);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (!product.getUser().getId().equals(user.getId())) {
            throw new ProductNotFoundException("Product not found");
        }

        return product;
    }

    public ProductResponseDTO updateProduct(int id, ProductRequestDTO requestDTO, String email) {
        Product product = getOwnedProduct(id, email);

        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + requestDTO.getCategoryId()));

        product.setName(requestDTO.getName());
        product.setPrice(requestDTO.getPrice());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    public void deleteProduct(int id, String email) {
        Product product = getOwnedProduct(id, email);
        productRepository.delete(product);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}