package com.anu.proj.youtech.Services;

import com.anu.proj.youtech.Dtos.CategoryDto;
import com.anu.proj.youtech.Dtos.PageableResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    //create category
    public CategoryDto createCategory(CategoryDto categoryDto);

    //get single category
    public CategoryDto getCategory(String id);

    //get all categories
    PageableResponse<CategoryDto> getAllCategories(int pageNumber,int pageSize,String sortBy,String sortDir);

    //delete a category
    public void deleteCategory(String id);

    //update a category

    public CategoryDto updateCategory(CategoryDto categoryDto,String id);

    //search category by title
    public List<CategoryDto> searchCategory(String title);
}
