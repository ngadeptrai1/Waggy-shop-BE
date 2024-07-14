package com.edu.authen.ultis;

import com.edu.authen.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import java.util.Map;

public class ProductSpecification implements Specification<Product> {
    private Map<String, Object> filterCriteria;

    public ProductSpecification(Map<String, Object> filterCriteria) {
        this.filterCriteria = filterCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (filterCriteria.get("category") != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(root.get("category").get("name"),"%" + filterCriteria.get("category")+ "%"));
        }

        if (filterCriteria.get("brand") != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(root.get("brand").get("name"),"%" +  filterCriteria.get("brand")+ "%"));
        }

        if (filterCriteria.get("name") != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(root.get("name"), "%" + filterCriteria.get("name") + "%"));
        }

        if (filterCriteria.get("minPrice") != null && filterCriteria.get("maxPrice") != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.between(root.get("salePrice"),
                            (Double) filterCriteria.get("minPrice"), (Double) filterCriteria.get("maxPrice")));
        }

        return predicate;
    }
}

