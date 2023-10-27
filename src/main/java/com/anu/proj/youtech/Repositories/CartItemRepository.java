package com.anu.proj.youtech.Repositories;

import com.anu.proj.youtech.Models.Cart;
import com.anu.proj.youtech.Models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,String> {

    void deleteByCart(Cart cart);
    List<CartItem> findByCart(Cart cart);
}
