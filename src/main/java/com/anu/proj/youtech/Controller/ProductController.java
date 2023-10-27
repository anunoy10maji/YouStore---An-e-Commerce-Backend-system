package com.anu.proj.youtech.Controller;

import com.anu.proj.youtech.Dtos.ApiResponseMessage;
import com.anu.proj.youtech.Dtos.PageableResponse;
import com.anu.proj.youtech.Dtos.ProductDto;
import com.anu.proj.youtech.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createproduct")
    public ResponseEntity<ProductDto> createProductCon(@RequestBody ProductDto productDto)
    {
        ProductDto productDto1=productService.createProduct(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateproduct/{pid}")
    public ResponseEntity<ProductDto> updateProductCon(@RequestBody ProductDto productDto,@PathVariable("pid") String pid)
    {
        ProductDto productDto1=productService.updateProduct(productDto,pid);
        return new ResponseEntity<>(productDto1,HttpStatus.OK);
    }

    @GetMapping("/getallproducts")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductsCon(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "1",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "productName",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "desc",required = false) String sortDir
    )
    {
        PageableResponse<ProductDto> productDtoPageableResponse=productService.getAllProducts(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(productDtoPageableResponse,HttpStatus.OK);
    }

    @GetMapping("/getproduct/{pid}")
    public ResponseEntity<ProductDto> getProductCon(@PathVariable("pid") String pid)
    {
        ProductDto productDto=productService.getProduct(pid);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @GetMapping("/searchproduct/{pname}")
    public ResponseEntity<List<ProductDto>> searchProductCon(@PathVariable("pname") String pname)
    {
        List<ProductDto> productDtoList=productService.searchProduct(pname);
        return new ResponseEntity<>(productDtoList,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteproduct/{pid}")
    public ResponseEntity<ApiResponseMessage> deleteProductCon(@PathVariable("pid") String pid)
    {
        productService.deleteProduct(pid);
        ApiResponseMessage responseMessage=ApiResponseMessage.builder().message("Product deleted Successfully")
                .success(true).status(HttpStatus.ACCEPTED).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.ACCEPTED);
    }
}
