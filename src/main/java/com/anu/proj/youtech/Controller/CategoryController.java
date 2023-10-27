package com.anu.proj.youtech.Controller;

import com.anu.proj.youtech.Dtos.ApiResponseMessage;
import com.anu.proj.youtech.Dtos.CategoryDto;
import com.anu.proj.youtech.Dtos.PageableResponse;
import com.anu.proj.youtech.Dtos.ProductDto;
import com.anu.proj.youtech.Services.CategoryService;
import com.anu.proj.youtech.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createcategory")
    public ResponseEntity<CategoryDto> createCategoryCon(@RequestBody CategoryDto categoryDto)
    {
        CategoryDto createdCategory=categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/getinglecategory/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategoryCon(@PathVariable("categoryId") String categoryId)
    {
        CategoryDto categoryDto=categoryService.getCategory(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @GetMapping("/getallcategory")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategoryCon(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "1",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "desc",required = false) String sortDir
    ){
        PageableResponse<CategoryDto> categoryDtoPageableResponse=categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(categoryDtoPageableResponse,HttpStatus.OK);
    }

    @GetMapping("/searchbytitle/{title}")
    public ResponseEntity<List<CategoryDto>> searchByTitleCon(@PathVariable("title") String title)
    {
        List<CategoryDto> categoryDtos=categoryService.searchCategory(title);
        return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updatecategory/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategoryCon(@RequestBody CategoryDto categoryDto,
                                                         @PathVariable("categoryId") String categoryId)
    {
        CategoryDto updatedCategory=categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletecategory/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable("categoryId") String categoryId)
    {
        categoryService.deleteCategory(categoryId);
        ApiResponseMessage res=ApiResponseMessage.builder().message("Category deleted successfully").
                status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{categoryid}/product")
    public ResponseEntity<ProductDto> createProductWithCategoryCon(@RequestBody ProductDto productDto,
                                                                   @PathVariable("categoryid") String cid)
    {
        ProductDto productDto1=productService.createProductWithCategory(productDto,cid);
        return new ResponseEntity<>(productDto1,HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryid}/product/{productid}")
    public ResponseEntity<ProductDto> updateProductWithCategoryCon(@PathVariable("categoryid") String cid,
                                                                   @PathVariable("productid") String pid)
    {
        ProductDto productDto=productService.updateProductWithCategory(pid,cid);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    @GetMapping("/{categoryid}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductsCategory(
            @PathVariable("categoryid") String cid,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "1",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "productName",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "desc",required = false) String sortDir
    )
    {
        PageableResponse<ProductDto> productDtoPageableResponse=productService.getAllProductsCategory(cid,
                pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(productDtoPageableResponse,HttpStatus.OK);
    }
}
