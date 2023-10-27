package com.anu.proj.youtech.Dtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private String cartIemId;
    private ProductDto productDto;
    private int quantity;
    private double totalPrice;
}
