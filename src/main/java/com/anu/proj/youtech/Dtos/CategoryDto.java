package com.anu.proj.youtech.Dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private String categoryId;
    private String title;
    private String description;
    private String coverImage;
    private List<ProductDto> productDtos=new ArrayList<>();
}
