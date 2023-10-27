package com.anu.proj.youtech.Dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private String productId;
    private String productName;
    private double price;
    private int quantity;
    private String description;
    private boolean stock;
    private double discountedPrice;
    private String productImage;
    private CategoryDto categoryDto;
}
