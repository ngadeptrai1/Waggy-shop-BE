package com.edu.authen.service.impl;

import com.edu.authen.DTO.BrandDTO;
import com.edu.authen.exceptions.DataNotFoundException;
import com.edu.authen.exceptions.DataPresentException;
import com.edu.authen.model.Brand;
import com.edu.authen.repository.BrandRepository;
import com.edu.authen.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    @Override
    public Brand findById(int id) {
        return brandRepository.findById( id).orElseThrow(() -> {
           return new DataNotFoundException("Brand with id "+id + " Not found ");
        });
    }

    @Override
    public Brand save(BrandDTO brand) {
        if(brandRepository.findByName(brand.getName()).isPresent()){
            throw new DataPresentException("Brand with name "+brand.getName()+ " already exist ");
        }
        Brand newBrand = Brand.builder()
                .name(brand.getName())
                .activate(brand.isActivate())
                .description(brand.getDescription())
                .build();
        return brandRepository.save(newBrand);
    }

    @Override
    public Brand update(Integer id, BrandDTO brandUpdate) {
        Brand brand = brandRepository.findById(id).orElseThrow(()->{
            return new DataNotFoundException("Brand with id "+id + " Not found ");
        });
        brand.setName(brandUpdate.getName());
        brand.setDescription(brandUpdate.getDescription());
        brand.setActivate(brandUpdate.isActivate());
        return brandRepository.save(brand);
    }
    @Override
    public void delete(Integer id) {
        try {
            brandRepository.delete(brandRepository.findById(id).orElseThrow(()->{
                return new DataNotFoundException("Brand with id "+id + " Not found ");
            }));
        }catch (Exception ex){
            throw new DataNotFoundException(ex.getMessage());
        }
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> findByName(String name) {
        return brandRepository.findByName(name);
    }
}
