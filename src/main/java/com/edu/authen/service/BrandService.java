package com.edu.authen.service;

import com.edu.authen.DTO.BrandDTO;
import com.edu.authen.model.Brand;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BrandService {
    Brand findById(int id);
    Brand save (BrandDTO brand);
    Brand update(Integer id, BrandDTO brand);
    void delete(Integer id);
    List<Brand> findAll(Pageable pageable);
    Optional<Brand> findByName(String name);
}
