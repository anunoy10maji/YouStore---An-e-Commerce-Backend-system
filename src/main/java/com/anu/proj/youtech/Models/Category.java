package com.anu.proj.youtech.Models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    private String categoryId;
    private String title;
    private String description;
    private String coverImage;
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List<Product> products=new ArrayList<>();
}
