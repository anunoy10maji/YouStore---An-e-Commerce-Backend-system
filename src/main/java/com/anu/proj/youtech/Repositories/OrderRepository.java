package com.anu.proj.youtech.Repositories;

import com.anu.proj.youtech.Models.Order;
import com.anu.proj.youtech.Models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {

    List<Order> findByUser(User user);
}
