package com.anu.proj.youtech.Services.impl;

import com.anu.proj.youtech.Dtos.CategoryDto;
import com.anu.proj.youtech.Dtos.PageableResponse;
import com.anu.proj.youtech.Exceptions.ResourceNotFound;
import com.anu.proj.youtech.Helpers.Helper;
import com.anu.proj.youtech.Models.Category;
import com.anu.proj.youtech.Repositories.CategoryRepository;
import com.anu.proj.youtech.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        String categoryId= UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category= mapper.map(categoryDto,Category.class);
        Category savedCategory=categoryRepository.save(category);
        return mapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(String id) {
        Category category=categoryRepository.findById(id).orElseThrow(()->new ResourceNotFound("Category not found"));
        CategoryDto categoryDto=mapper.map(category,CategoryDto.class);
        return categoryDto;
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort s=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,s);
        Page<Category> page=categoryRepository.findAll(pageable);
        return Helper.getPageableResponse(page,CategoryDto.class);
    }

    @Override
    public void deleteCategory(String id) {
        Category category=categoryRepository.findById(id).orElseThrow(()->new ResourceNotFound("Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto,String id) {
        Category category=categoryRepository.findById(id).orElseThrow(()->new ResourceNotFound("Category not found"));
        category.setTitle(categoryDto.getCategoryId());
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory=categoryRepository.save(category);
        return mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> searchCategory(String title) {
        List<Category> categories=categoryRepository.findByTitleContaining(title);
        List<CategoryDto> categoryDtoList=categories.stream().map(category ->
                mapper.map(category,CategoryDto.class)).collect(Collectors.toList());
        return categoryDtoList;
    }
}
