package com.edu.authen.service;

import com.edu.authen.DTO.CategoryDTO;
import com.edu.authen.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {
    Category findById(int id);
    Category save (CategoryDTO category);
    Category update( int id , CategoryDTO category);
    List<Category> findAll(Pageable pageable);
    Optional<Category> findByName(String name);
    void delete(int id);
}
