package com.edu.authen.controller;

import com.edu.authen.DTO.CategoryDTO;
import com.edu.authen.exceptions.DataInvalidException;
import com.edu.authen.model.Category;
import com.edu.authen.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping({"","/"})
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(categoryService.findAll().stream().map(category -> {
                    return convert(category);
                }));
    }

    @PostMapping({"","/"})
    public ResponseEntity<?> insert( @Valid @RequestBody CategoryDTO category ,
                                    BindingResult result){
        if(result.hasErrors()){
            List<String> errs = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
            throw new DataInvalidException( "Err",errs);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(convert(categoryService.save(category)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update( @PathVariable int id,
                                    @Valid @RequestBody CategoryDTO updateCate ,
                                    BindingResult result){
        if(result.hasErrors()){
            List<String> errs = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
            throw new DataInvalidException( "Err",errs);
        }
        return ResponseEntity.status(HttpStatus.OK).body(convert(categoryService.update(id,updateCate)));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.FOUND).body(convert(categoryService.findById(id)));
    }
    private CategoryDTO convert(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .description(category.getDescription())
                .name(category.getName())
                .activate(category.isActivate())
                .build();
    }
}
