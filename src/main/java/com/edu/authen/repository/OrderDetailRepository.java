package com.edu.authen.repository;

import com.edu.authen.model.OrderDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail , Long> {
    List<OrderDetail> findOrderDetailsByOrderId(Long id);

}
