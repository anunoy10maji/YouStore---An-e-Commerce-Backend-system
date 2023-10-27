package com.anu.proj.youtech.Services;

import com.anu.proj.youtech.Dtos.PageableResponse;
import com.anu.proj.youtech.Dtos.ProductDto;

import java.security.PublicKey;
import java.util.List;

public interface ProductService {

    public ProductDto createProduct(ProductDto productDto);
    public PageableResponse<ProductDto> getAllProducts(int pageNumber,int pageSize,String sortBy,String sortDir);
    public ProductDto getProduct(String id);
    public ProductDto updateProduct(ProductDto productDto,String id);
    public void deleteProduct(String id);
    public List<ProductDto> searchProduct(String productName);
    public ProductDto createProductWithCategory(ProductDto productDto,String categoryId);
    public ProductDto updateProductWithCategory(String pid,String cid);
    public PageableResponse<ProductDto> getAllProductsCategory(String cid,int pageNumber,int pageSize,String sortBy,String sortDir);
}
