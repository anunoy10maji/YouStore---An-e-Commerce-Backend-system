package com.anu.proj.youtech.Dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private String orderId;
    private String address;
    private String phoneNumber;
    private boolean orderStatus;
    private double orderAmount;
    private String paymentMode;
    private boolean paymentStatus;
    private Date billingDate=new Date();
    private Date deliveredDate;
    private UserDto user;
    private List<OrderItemDto> orderItems=new ArrayList<>();
}
