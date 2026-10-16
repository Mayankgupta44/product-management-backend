package com.example.restAPIs_practice.Controller;

import com.example.restAPIs_practice.DTO.ProductRequestDTO;
import com.example.restAPIs_practice.DTO.ProductResponseDTO;
import com.example.restAPIs_practice.Service.ProductService;
import com.example.restAPIs_practice.Validator.ProductValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final Validator validator;

    public ProductController(ProductService productService , @Qualifier("productValidator") Validator validator){
        this.productService = productService;
        this.validator = validator;
    }

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.addValidators(validator);
//    }

    @GetMapping
    public List<ProductResponseDTO> getAll(Authentication authentication) {
        return productService.findAllProducts(authentication.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getOne(@PathVariable int id, Authentication authentication) {
        return ResponseEntity.ok(productService.getProductById(id, authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductRequestDTO requestDTO, Authentication authentication) {
        ProductResponseDTO response = productService.createProduct(authentication.getName(), requestDTO);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable int id, @Valid @RequestBody ProductRequestDTO requestDTO, Authentication authentication) {
        ProductResponseDTO response = productService.updateProduct(id, requestDTO, authentication.getName());
        return ResponseEntity.ok(response);
    }

//    @PatchMapping(path = "/{id}")
//    public Product patchProduct(@PathVariable(name="id") int productId, @RequestBody Product product) {
//        for (Product p : products) {
//            if (p.getId() == productId) {
//                p.setName(product.getName());
//                p.setPrice(product.getPrice());
//                return p;
//            }
//        }
//        return product;
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id, Authentication authentication) {
        productService.deleteProduct(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam(defaultValue = "all") String category, @RequestParam(required = false)  Double maxPrice) {
        return category + "  under  " + maxPrice;
    }
}
