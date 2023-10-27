package com.anu.proj.youtech.Services;

import com.anu.proj.youtech.Dtos.AddToCartRequest;
import com.anu.proj.youtech.Dtos.CartDto;

public interface CartService {

    public CartDto addItemToCart(String userId, AddToCartRequest req);
    public int removeItemToCart(String userId,String cartItemId);
    public void clearCart(String userId);

    public CartDto getCart(String userId);
}
