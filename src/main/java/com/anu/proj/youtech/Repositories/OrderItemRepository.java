package com.anu.proj.youtech.Repositories;


import com.anu.proj.youtech.Models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository  extends JpaRepository<OrderItem,String> {
}
