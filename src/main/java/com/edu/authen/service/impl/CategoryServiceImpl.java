package com.edu.authen.service.impl;

import com.edu.authen.DTO.CategoryDTO;
import com.edu.authen.exceptions.DataNotFoundException;
import com.edu.authen.exceptions.DataPresentException;
import com.edu.authen.model.Category;
import com.edu.authen.repository.CategoryRepository;
import com.edu.authen.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id).orElseThrow(() ->{
            return new DataNotFoundException("Not found category with id " + id);
        });
    }

    @Override
    public Category save(CategoryDTO category) {
        if (categoryRepository.findByName(category.getName()).isPresent()){
          throw new DataPresentException("Category with name "+ category.getName()+ " already exist ");
        }
        Category newCategory = Category.builder()
                .name(category.getName())
                .description(category.getDescription())
                .activate(category.isActivate())
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category update( int id ,  CategoryDTO categoryUpdate) {
        Category category = this.findById(id);
        category.setName(category.getName());
        category.setActivate(category.isActivate());
        category.setDescription(category.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public void delete(int id) {
        try {
            categoryRepository.delete(this.findById(id));
        }catch (Exception ex){
            throw new DataNotFoundException(ex.getMessage());
        }
    }

}
