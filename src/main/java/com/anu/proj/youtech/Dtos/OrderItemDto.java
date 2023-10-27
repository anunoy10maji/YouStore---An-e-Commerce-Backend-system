package com.anu.proj.youtech.Dtos;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private String orderItemId;
    private int quantity;
    private double totalPrice;
    private ProductDto product;
}
