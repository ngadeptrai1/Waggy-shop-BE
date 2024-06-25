package com.edu.authen.service;

import com.edu.authen.DTO.OrderDTO;
import com.edu.authen.model.Order;
import com.edu.authen.response.OrderResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {

    List<OrderResponse> findAll(Pageable request);
    OrderResponse create(OrderDTO order);
    OrderResponse findById(Long id);
    OrderResponse update(OrderDTO order,long id);
    List<OrderResponse> findByUserId(Long id,Pageable request);
    OrderResponse changeStatus(Order order, String status);
    OrderResponse delete(Long id);

}
