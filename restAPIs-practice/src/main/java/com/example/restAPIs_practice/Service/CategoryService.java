package com.example.restAPIs_practice.Service;

import com.example.restAPIs_practice.DTO.CategoryDTO;
import com.example.restAPIs_practice.Entity.Category;
import com.example.restAPIs_practice.Exception.CategoryInUseException;
import com.example.restAPIs_practice.Exception.CategoryNotFoundException;
import com.example.restAPIs_practice.Mapper.CategoryMapper;
import com.example.restAPIs_practice.Repository.CategoryRepository;
import com.example.restAPIs_practice.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, ProductRepository productRepository){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.productRepository = productRepository;
    }

    public CategoryDTO createCategory(CategoryDTO dto){
        Category category = categoryMapper.toEntity(dto);
        return categoryMapper.toResponseDTO(categoryRepository.save(category));
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponseDTO)
                .toList();
    }

    public CategoryDTO getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        return categoryMapper.toResponseDTO(category);
    }

    public CategoryDTO updateCategory(Integer id, CategoryDTO dto){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponseDTO(savedCategory);
    }

    public void deleteCategory(Integer id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        if (productRepository.existsByCategoryId(id)) {
            throw new CategoryInUseException("Cannot delete category because products are associated with it");
        }

        categoryRepository.deleteById(id);
    }

}
