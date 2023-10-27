package com.anu.proj.youtech.Services.impl;

import com.anu.proj.youtech.Dtos.CategoryDto;
import com.anu.proj.youtech.Dtos.PageableResponse;
import com.anu.proj.youtech.Dtos.ProductDto;
import com.anu.proj.youtech.Exceptions.ResourceNotFound;
import com.anu.proj.youtech.Helpers.Helper;
import com.anu.proj.youtech.Models.Category;
import com.anu.proj.youtech.Models.Product;
import com.anu.proj.youtech.Repositories.CategoryRepository;
import com.anu.proj.youtech.Repositories.ProductRepository;
import com.anu.proj.youtech.Services.ProductService;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    ModelMapper mapper;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        String pid= UUID.randomUUID().toString();
        productDto.setProductId(pid);
        Product product=mapper.map(productDto,Product.class);
        Product savedProduct=productRepository.save(product);
        return mapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort s=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,s);
        Page<Product> page=productRepository.findAll(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public ProductDto getProduct(String id) {
        Product product=productRepository.findById(id).orElseThrow(()->new ResourceNotFound("Product not found"));
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String id) {
        Product product=productRepository.findById(id).orElseThrow(()->new ResourceNotFound("Product not found"));
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setStock(productDto.isStock());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setProductImage(productDto.getProductImage());
        Product updatedProduct=productRepository.save(product);
        return mapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void deleteProduct(String id) {
        Product product=productRepository.findById(id).orElseThrow(()->new ResourceNotFound("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> searchProduct(String productName) {
        List<Product> productList=productRepository.findByProductNameContaining(productName);
        List<ProductDto> productDtoList=productList.stream().map(product ->
                mapper.map(product,ProductDto.class)).collect(Collectors.toList());
        return productDtoList;
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFound("Category not found"));
        CategoryDto categoryDto=mapper.map(category,CategoryDto.class);
        productDto.setCategoryDto(categoryDto);
        String pid= UUID.randomUUID().toString();
        productDto.setProductId(pid);
        Product product=mapper.map(productDto,Product.class);
        productRepository.save(product);
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public ProductDto updateProductWithCategory(String pid, String cid) {
        Product product=productRepository.findById(pid).orElseThrow(()->new ResourceNotFound("Product not found"));
        Category category=categoryRepository.findById(cid).orElseThrow(()->new ResourceNotFound("Category not found"));
        product.setCategory(category);
        productRepository.save(product);
        ProductDto productDto=mapper.map(product,ProductDto.class);
        return productDto;
    }

    @Override
    public PageableResponse<ProductDto> getAllProductsCategory(String cid,
                                                               int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort s=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,s);
        Category category=categoryRepository.findById(cid).orElseThrow(()->new ResourceNotFound("Category not found"));
        Page<Product> products=productRepository.findByCategory(category,pageable);
        return Helper.getPageableResponse(products,ProductDto.class);
    }


}
