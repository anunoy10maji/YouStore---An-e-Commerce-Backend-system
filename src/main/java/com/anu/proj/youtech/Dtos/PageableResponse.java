package com.anu.proj.youtech.Dtos;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PageableResponse <T>{
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private int totalPage;
    private boolean lastPage;
}
