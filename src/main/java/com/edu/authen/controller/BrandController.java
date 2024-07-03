package com.edu.authen.controller;

import com.edu.authen.DTO.BrandDTO;
import com.edu.authen.exceptions.DataInvalidException;
import com.edu.authen.model.Brand;
import com.edu.authen.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/brands")
public class BrandController {
    private final BrandService brandService;

    @GetMapping({"","/"})
    public ResponseEntity<?> findAll(@RequestParam("page") Optional<Integer> pageNum,
                                     @RequestParam("size") Optional<Integer> size,
                                     @RequestParam("sort")Optional<String> sort){
        Pageable page = PageRequest.of(pageNum.orElse(0),size.orElse(5), Sort.by(sort.orElse("id")).descending());
        try {
            return ResponseEntity.status(HttpStatus.OK).body(brandService.findAll(page));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.FOUND).body(convert(brandService.findById(id)));
    }

    @PostMapping({"","/"})
    public ResponseEntity<?> create(@Valid @RequestBody BrandDTO brand ,
                                    BindingResult result){

        if(result.hasErrors()){
            List<String> errs = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
            String mess = errs.isEmpty() ? "" : errs.get(0);;
            throw new DataInvalidException( mess);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(convert(brandService.save(brand)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody BrandDTO updateBrand ,
                                    BindingResult result,
                                    @PathVariable Integer id){
        if(result.hasErrors()){
            List<String> errs = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
            throw new DataInvalidException( errs.isEmpty() ? "" : errs.get(0));
        }
        return ResponseEntity.status(HttpStatus.OK).body(convert(brandService.update(id , updateBrand)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        return ResponseEntity.ok().body("ok");
    }

    private BrandDTO convert(Brand brand){
        return  BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .activate(brand.isActivate())
                .build();
    }


}
