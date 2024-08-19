package com.edu.authen.service.impl;

import com.edu.authen.DTO.OrderDTO;
import com.edu.authen.DTO.OrderDetailDTO;
import com.edu.authen.exceptions.DataInvalidException;
import com.edu.authen.exceptions.DataNotFoundException;
import com.edu.authen.model.*;
import com.edu.authen.repository.OrderDetailRepository;
import com.edu.authen.repository.OrderRepository;
import com.edu.authen.repository.ProductRepository;
import com.edu.authen.repository.UserRepository;
import com.edu.authen.response.OrderDetailResponse;
import com.edu.authen.response.OrderResponse;
import com.edu.authen.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderDetailRepository detailRepo;

    private final UserRepository userRepo;

    private final ProductRepository productRepo;

    private final ModelMapper modelMapper;

    @Override
    public List<OrderResponse> findAll(Pageable request) {
        return orderRepository.findAll(request).map(order -> {
            modelMapper.typeMap(Order.class,OrderResponse.class);
          return modelMapper.map(order,OrderResponse.class);
        }).stream().toList();
    }

    @Override
    public List<OrderResponse> findByUserId(Long id, Pageable request) {
        return orderRepository.findAllByUserId(id,request).stream().map(order -> {
            modelMapper.typeMap(Order.class,OrderResponse.class);
            return modelMapper.map(order,OrderResponse.class);
        }).toList();
    }

    @Override
    @Transactional(rollbackFor = {DataNotFoundException.class, Exception.class,NullPointerException.class})
    public OrderResponse create(OrderDTO orderDTO) {
        if(orderDTO.getOrderDetails().isEmpty()|| orderDTO.getOrderDetails() == null ){
            throw new NullPointerException("Order must have more than 1 product");
        }
        User user = userRepo.findById(orderDTO.getUserId()).orElseThrow(()->
         new DataNotFoundException("Not found user")
        );
        modelMapper.typeMap(OrderDTO.class,Order.class)
        .addMappings(mapper ->mapper.skip(Order::setId))
        .addMappings(mapper -> mapper.skip(Order::setQuantityProduct))
        .addMappings(mapper -> mapper.skip(Order::setOrderDetails))
        .addMappings(mapper -> mapper.skip(Order::setDiscountMoney))
        .addMappings(mapper -> mapper.skip(Order::setTotalMoney));
       Order order = new Order();
       modelMapper.map(orderDTO,order);

       order.setNote(orderDTO.getNote());

        order.setQuantityProduct(orderDTO.getOrderDetails().size());

       order.setUser(user);
      order.setCreatedDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

      order = orderRepository.save(order) ;
        List<OrderDetail> listItems = new ArrayList<>();
        for (OrderDetailDTO detail:
             orderDTO.getOrderDetails()) {

            Product pr = productRepo.findById(detail.getProductId()).orElseThrow(
                    ()->{ return new DataNotFoundException( "Not found product with id = " + detail.getProductId());});

            OrderDetail orderDetail = OrderDetail.builder()
                    .product(pr)
                    .quantity(detail.getQuantity())
                    .order(order)
                    .price(pr.getSalePrice())

                    .build();
            listItems.add(orderDetail);
        }
        order.setOrderDetails( detailRepo.saveAll(listItems));
        Float prices = Float.valueOf(order.getOrderDetails().stream().mapToDouble(orderDetail -> {
            return orderDetail.getPrice()*orderDetail.getQuantity();
        }).sum()+"");
        order.setTotalMoney(prices);
        order.setDiscountMoney(prices);
        orderRepository.save(order);
        listItems = null;
        modelMapper.typeMap(Order.class,OrderResponse.class);
        OrderResponse response =  modelMapper.map(order,OrderResponse.class);
            response.setNote(order.getNote());
       List<OrderDetailResponse> responseList = order.getOrderDetails().stream().map(orderDetail ->
       {
           Double price = orderDetail.getPrice() * orderDetail.getQuantity();
           return OrderDetailResponse.builder()
               .orderId(orderDetail.getOrder().getId())
               .quantity(orderDetail.getQuantity())
               .totalMoney(price)
               .productId(orderDetail.getProduct().getId())
               .id(orderDetail.getId())
               .price(orderDetail.getPrice())
               .build(); }).toList();
       response.setOrderDetails(responseList);
        return response;
    }

    @Override
    public OrderResponse findById(Long id) {
        Order order =  orderRepository.findById(id).orElseThrow
                (()->{ return new DataNotFoundException("Not found order with id = "+id);});
        modelMapper.typeMap(Order.class,OrderResponse.class);
        return modelMapper.map(order,OrderResponse.class);
    }


    @Override
    public OrderResponse update(OrderDTO order,long id) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(()->{
            return new DataNotFoundException("Not found order with id = "+id);
        });
        User u = userRepo.findById(order.getUserId()).orElseThrow(()->{
            return  new DataNotFoundException("Not found order with id  = "+order.getUserId());
        });
        modelMapper.typeMap(OrderDTO.class,Order.class)
                .addMappings(mapper ->mapper.skip(Order::setId));
        modelMapper.map(order,existingOrder);
        orderRepository.save(existingOrder);
        modelMapper.typeMap(Order.class,OrderResponse.class);
        return modelMapper.map(existingOrder,OrderResponse.class);
    }


    @Override
    public OrderResponse changeStatus(Order order, String status) {
        return null;
    }

    @Override
    public OrderResponse delete(Long id) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(()->{
            return new DataNotFoundException("Not found order with id = "+id);
        });
        existingOrder.setStatus(OrderStatus.CANCELLED);
        modelMapper.typeMap(Order.class,OrderResponse.class);
        return modelMapper.map(existingOrder,OrderResponse.class);
    }
}
