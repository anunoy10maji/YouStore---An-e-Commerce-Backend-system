package com.anu.proj.youtech.Repositories;

import com.anu.proj.youtech.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category,String> {

    public List<Category> findByTitleContaining(String title);
}
