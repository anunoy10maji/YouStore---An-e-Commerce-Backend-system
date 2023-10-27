package com.anu.proj.youtech.Repositories;

import com.anu.proj.youtech.Models.Cart;
import com.anu.proj.youtech.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findByUser(User user);
}
