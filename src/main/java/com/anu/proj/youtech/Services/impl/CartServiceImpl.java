package com.anu.proj.youtech.Services.impl;

import com.anu.proj.youtech.Dtos.AddToCartRequest;
import com.anu.proj.youtech.Dtos.CartDto;
import com.anu.proj.youtech.Exceptions.ResourceNotFound;
import com.anu.proj.youtech.Models.Cart;
import com.anu.proj.youtech.Models.CartItem;
import com.anu.proj.youtech.Models.Product;
import com.anu.proj.youtech.Models.User;
import com.anu.proj.youtech.Repositories.CartItemRepository;
import com.anu.proj.youtech.Repositories.CartRepository;
import com.anu.proj.youtech.Repositories.ProductRepository;
import com.anu.proj.youtech.Repositories.UserRepository;
import com.anu.proj.youtech.Services.CartService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public CartDto addItemToCart(String userId, AddToCartRequest req) {

        String productId=req.getProductId();
        int quantity=req.getQuantity();
        Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFound("Product not found!"));
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFound("User not found!"));
        Cart cart=null;
        try {
            cart= cartRepository.findByUser(user).get();
        }catch (NoSuchElementException e){
            String cartId= UUID.randomUUID().toString();
            cart=new Cart();
            cart.setCartId(cartId);
        }

        List<CartItem> items=cart.getCartItemList();

        AtomicReference<Boolean> update=new AtomicReference<>(false);
        items=items.stream().map(cartItem -> {
            if(cartItem.getProduct().getProductId().equals(productId))
            {
                cartItem.setQuantity(quantity);
                cartItem.setTotalPrice(quantity*(product.getDiscountedPrice()));
                update.set(true);
            }
            return cartItem;
        }).collect(Collectors.toList());


        if (update.get()==false)
        {
            String cartItemId=UUID.randomUUID().toString();
            CartItem cartItem=CartItem.builder().cartIemId(cartItemId).product(product).
                    quantity(quantity).totalPrice(quantity*(product.getDiscountedPrice())).cart(cart).build();
            cart.getCartItemList().add(cartItem);
        }
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart, CartDto.class);
    }

    @Override
    public int removeItemToCart(String userId, String cartItemId) {
        CartItem cartItem=cartItemRepository.findById(cartItemId).orElseThrow(()->new ResourceNotFound("Item not found!"));
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFound("User not found!"));
        Cart cart=cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFound("Cart is empty"));
        List<CartItem> cartItemList=cart.getCartItemList();
        AtomicReference<Boolean> update=new AtomicReference<>(false);
        List<CartItem> updatedCartItems=cartItemList.stream().map(cartItem1 -> {
            if(cartItem1.getCartIemId().equals(cartItemId))
            {
                update.set(true);
            }
            return cartItem1;
        }).collect(Collectors.toList());
        System.out.println(update.get());
        if(update.get()==true)
        {
            cartItemList.remove(cartItem);
            cartItemRepository.deleteById(cartItemId);
            Cart updatedCart=cartRepository.save(cart);
            return 1;
        }
        return 0;
    }

    @Override
    public void clearCart(String userId) {

        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFound("User not found!"));
        Cart cart=cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFound("Please add something to cart"));
        List<CartItem> cartItemList=cart.getCartItemList();
        if(cartItemList.size()==0)
        {
            throw new ResourceNotFound("please add something to cart first");
        }
        cartItemList.clear();
        cart.setCartItemList(cartItemList);
        Cart updatedCart=cartRepository.save(cart);
        cartItemRepository.deleteByCart(updatedCart);
    }

    @Override
    public CartDto getCart(String userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFound("User not found!"));
        Cart cart=cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFound("Please add something to cart"));
        return mapper.map(cart,CartDto.class);
    }
}
