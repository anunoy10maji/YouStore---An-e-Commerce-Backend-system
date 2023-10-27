package com.anu.proj.youtech.Controller;

import com.anu.proj.youtech.Dtos.AddToCartRequest;
import com.anu.proj.youtech.Dtos.ApiResponseMessage;
import com.anu.proj.youtech.Dtos.CartDto;
import com.anu.proj.youtech.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/additem/{userid}")
    public ResponseEntity<CartDto> addCartItem(@PathVariable("userid") String userid, @RequestBody AddToCartRequest req)
    {
        CartDto cartDto=cartService.addItemToCart(userid,req);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
    @DeleteMapping("/removeCartItem/{userid}/{cartItemId}")
    public ResponseEntity<ApiResponseMessage> removeCartItem(@PathVariable("userid") String userid,@PathVariable("cartItemId") String cartitemid)
    {
        int res=cartService.removeItemToCart(userid,cartitemid);
        ApiResponseMessage responseMessage = null;
        if(res==1)
        {
            responseMessage=ApiResponseMessage.builder().message("Item deleted from cart successfully").
                    success(true).status(HttpStatus.OK).build();
        }
        else if(res==0)
        {
            responseMessage=ApiResponseMessage.builder().message("No such Item found for this user").
                    success(true).status(HttpStatus.OK).build();
        }
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @DeleteMapping("/clearCart/{userid}")
    public ResponseEntity<ApiResponseMessage> clearFullCart(@PathVariable("userid") String userid)
    {
        cartService.clearCart(userid);
        ApiResponseMessage responseMessage=ApiResponseMessage.builder().message("Cart is empty").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @GetMapping("/getCart/{userid}")
    public ResponseEntity<CartDto> getFullCart(@PathVariable("userid") String userid)
    {
        CartDto cartDto=cartService.getCart(userid);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }
}
