package com.edu.authen.service.impl;

import com.edu.authen.DTO.OrderDTO;
import com.edu.authen.exceptions.DataInvalidException;
import com.edu.authen.exceptions.DataNotFoundException;
import com.edu.authen.model.Order;
import com.edu.authen.model.OrderDetail;
import com.edu.authen.model.OrderStatus;
import com.edu.authen.repository.OrderDetailRepository;
import com.edu.authen.repository.OrderRepository;
import com.edu.authen.repository.ProductRepository;
import com.edu.authen.repository.UserRepository;
import com.edu.authen.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderDetailRepository detailRepo;

    private final UserRepository userRepo;

    private final ProductRepository productRepo;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order create(OrderDTO order) {
        if(order.getOrderDetails().isEmpty()|| order.getOrderDetails() == null ){
            throw new NullPointerException("Order must have more than 1 product");
        }
        if(!userRepo.existsById(order.getUserId())){
            throw new DataNotFoundException("Not found user");
        }
        Order orderRepo = Order.builder()
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .user(userRepo.findById(order.getUserId()).get())
                .note(order.getNote())
                .fullName(order.getFullName())
                .quantityProduct(order.getOrderDetails().size())
                .status(OrderStatus.PENDING)

                .build();

//        return orderRepository.save(order) ;
        return null;
    }


    @Override
    public Order update(OrderDTO order) {
//        return orderRepository.save(order);
        return null;
    }

    @Override
    public List<Order> findByUserId(Long id) {
        return orderRepository.findAllByUserId(id);
    }

    @Override
    public Order changeStatus(Order order, String status) {



        return null;
    }
}
