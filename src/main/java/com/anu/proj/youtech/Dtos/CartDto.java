package com.anu.proj.youtech.Dtos;




import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    private String cartId;
    private UserDto userDto;
    private List<CartItemDto> cartItemListDto=new ArrayList<>();
}
