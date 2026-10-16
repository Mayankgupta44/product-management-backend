package com.example.restAPIs_practice.Repository;

import com.example.restAPIs_practice.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String trim);

    boolean existsByNameAndIdNot(String name, Integer id);
}
