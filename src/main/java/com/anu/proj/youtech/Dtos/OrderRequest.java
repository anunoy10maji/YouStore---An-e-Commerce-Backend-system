package com.anu.proj.youtech.Dtos;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private String userId;
    private String cartId;
    private String address;
    private boolean orderStatus;
    private String phoneNumber;
    private String paymentMode;
    private boolean paymentStatus;


}
