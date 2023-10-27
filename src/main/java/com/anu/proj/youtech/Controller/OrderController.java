package com.anu.proj.youtech.Controller;

import com.anu.proj.youtech.Dtos.ApiResponseMessage;
import com.anu.proj.youtech.Dtos.OrderDto;
import com.anu.proj.youtech.Dtos.OrderRequest;
import com.anu.proj.youtech.Dtos.PageableResponse;
import com.anu.proj.youtech.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> createOrderCon(@RequestBody OrderRequest request)
    {
        OrderDto orderDto=orderService.createOrder(request);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }
    @GetMapping("/getOrdersOf/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable("userId") String userId)
    {
        List<OrderDto> orderDtos=orderService.getOrdersOfUsers(userId);
        return new ResponseEntity<>(orderDtos,HttpStatus.OK);
    }
    @GetMapping("/getOrders")
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrders(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "1",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "billingDate",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "desc",required = false) String sortDir
    )
    {
        PageableResponse<OrderDto> orderDtoPageableResponse=orderService.getOrders(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(orderDtoPageableResponse,HttpStatus.OK);
    }
    @PutMapping("/update/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderRequest request,@PathVariable("orderId") String orderId)
    {
        OrderDto orderDto=orderService.updateOrder(request,orderId);
        return new ResponseEntity<>(orderDto,HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<ApiResponseMessage> deleteOrder(@PathVariable("orderId") String orderId)
    {
        orderService.deleteOrder(orderId);
        ApiResponseMessage responseMessage=ApiResponseMessage.builder().message("Order deleted successfully").
                success(true).status(HttpStatus.OK).build();

        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }
}
