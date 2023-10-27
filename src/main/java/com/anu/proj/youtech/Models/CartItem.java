package com.anu.proj.youtech.Models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    private String cartIemId;
    @OneToOne
    @JoinColumn(name = "ProductId")
    private Product product;
    private int quantity;
    private double totalPrice;
    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;
}
