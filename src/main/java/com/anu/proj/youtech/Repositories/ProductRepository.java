package com.anu.proj.youtech.Repositories;

import com.anu.proj.youtech.Models.Category;
import com.anu.proj.youtech.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    public List<Product> findByProductNameContaining(String name);
    //Page<Product> findByCategory(Category category);

    Page<Product> findByCategory(Category category, Pageable pageable);
}
