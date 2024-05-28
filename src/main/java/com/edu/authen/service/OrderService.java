package com.edu.authen.service;

import com.edu.authen.DTO.OrderDTO;
import com.edu.authen.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    List<Order> findAll();
    Order create(OrderDTO order);
    Order update(OrderDTO order);
    List<Order> findByUserId(Long id);
    Order changeStatus(Order order, String status);
}
