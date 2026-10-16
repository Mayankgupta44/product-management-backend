package com.example.restAPIs_practice.Repository;

import com.example.restAPIs_practice.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByCategoryId(Integer categoryId);
    List<Product> findByUser_Id(Integer userId);
}
