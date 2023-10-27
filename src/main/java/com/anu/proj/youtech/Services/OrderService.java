package com.anu.proj.youtech.Services;

import com.anu.proj.youtech.Dtos.OrderDto;
import com.anu.proj.youtech.Dtos.OrderRequest;
import com.anu.proj.youtech.Dtos.PageableResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderRequest request);
    List<OrderDto> getOrdersOfUsers(String userId);
    PageableResponse<OrderDto> getOrders(int pageNumber,int pageSize,String sortBy,String sortDir);
    void deleteOrder(String orderId);

    OrderDto updateOrder(OrderRequest request,String orderId);
}
