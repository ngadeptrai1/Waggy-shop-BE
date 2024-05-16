package com.edu.authen.service;

import com.edu.authen.DTO.CategoryDTO;
import com.edu.authen.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {
    Category findById(int id);
    Category save (CategoryDTO category);
    Category update( int id , CategoryDTO category);
    List<Category> findAll();
    Optional<Category> findByName(String name);
    void delete(int id);
}
