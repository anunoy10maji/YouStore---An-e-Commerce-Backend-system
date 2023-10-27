package com.anu.proj.youtech.Services.impl;

import com.anu.proj.youtech.Dtos.OrderDto;
import com.anu.proj.youtech.Dtos.OrderRequest;
import com.anu.proj.youtech.Dtos.PageableResponse;
import com.anu.proj.youtech.Exceptions.ResourceNotFound;
import com.anu.proj.youtech.Helpers.Helper;
import com.anu.proj.youtech.Models.*;
import com.anu.proj.youtech.Repositories.CartRepository;
import com.anu.proj.youtech.Repositories.OrderItemRepository;
import com.anu.proj.youtech.Repositories.OrderRepository;
import com.anu.proj.youtech.Repositories.UserRepository;
import com.anu.proj.youtech.Services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    ModelMapper mapper;
    @Override
    public OrderDto createOrder(OrderRequest request) {

        String userId=request.getUserId();
        String cartId=request.getCartId();

        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFound("user not found"));
        Cart cart=cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFound("Cart is empty"));

        List<CartItem> cartItemList=cart.getCartItemList();
        if(cartItemList.size()==0)
        {
            throw new ResourceNotFound("Cart is already empty!!");
        }

        Order order=Order.builder().orderId(UUID.randomUUID().toString()).address(request.getAddress()).
                phoneNumber(request.getPhoneNumber()).orderStatus(true).deliveredDate(null).billingDate(new Date()).
                paymentStatus(request.isPaymentStatus()).paymentMode(request.getPaymentMode()).user(user).build();

        AtomicReference<Double> orderAmount=new AtomicReference<>(0.0);
        List<OrderItem> orderItems=cartItemList.stream().map(cartItem -> {
            OrderItem orderItem=OrderItem.builder().orderItemId(UUID.randomUUID().toString()).
                    quantity(cartItem.getQuantity()).totalPrice(cartItem.getTotalPrice()).
                    product(cartItem.getProduct()).order(order).build();
            orderAmount.set(orderAmount.get()+orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        cartItemList.clear();

        Order savedOrder=orderRepository.save(order);
        return mapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public List<OrderDto> getOrdersOfUsers(String userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFound("user not found"));
        List<Order> orders=orderRepository.findByUser(user);

        List<OrderDto> orderDtos=orders.stream().map(order -> {
            return mapper.map(order,OrderDto.class);
        }).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort s=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,s);
        Page<Order> orders=orderRepository.findAll(pageable);
        return Helper.getPageableResponse(orders,OrderDto.class);
    }

    @Override
    public void deleteOrder(String orderId) {
        Order order=orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFound("Order not found"));
        orderRepository.delete(order);
    }

    @Override
    public OrderDto updateOrder(OrderRequest request,String orderId) {

        Order order=orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFound("Order not found"));
        order.setOrderStatus(request.isOrderStatus());
        order.setAddress(request.getAddress());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setPaymentMode(request.getPaymentMode());
        orderRepository.save(order);
        return mapper.map(order,OrderDto.class);
    }
}
