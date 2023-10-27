package com.anu.proj.youtech.Controller;

import com.anu.proj.youtech.Dtos.ApiResponseMessage;
import com.anu.proj.youtech.Dtos.PageableResponse;
import com.anu.proj.youtech.Dtos.UserDto;
import com.anu.proj.youtech.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService uservice;
    @PostMapping("/createuser")
    public ResponseEntity<UserDto> createUserCon(@RequestBody UserDto userDto)
    {
        UserDto createdUser=uservice.createUser(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @PutMapping("/updateuser/{userid}")
    public ResponseEntity<UserDto> updateUserCon(@RequestBody UserDto userDto,@PathVariable("userid") String userid)
    {
        UserDto updatedUser=uservice.updateUser(userDto,userid);
        return  new ResponseEntity<>(updatedUser,HttpStatus.ACCEPTED);
    }
    @GetMapping("/getusers")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsersCon(
            @RequestParam(value = "PageNumber",defaultValue = "0",required = false) int pagenumber,
            @RequestParam(value = "PageSize",defaultValue = "1",required = false) int pagesize,
            @RequestParam(value = "SortBy",defaultValue = "name",required = false) String sortby,
            @RequestParam(value = "SortDir",defaultValue = "desc",required = false) String sortdir
    )
    {
        PageableResponse<UserDto> allUsers=uservice.getAllUsers(pagenumber,pagesize,sortby,sortdir);
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }
    @GetMapping("/getsingleuser/{userid}")
    public ResponseEntity<UserDto> getUserCon(@PathVariable("userid") String userid)
    {
        UserDto user=uservice.getUser(userid);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/getuserbyemail/{email}")
    public ResponseEntity<UserDto> getUserByMailCon(@PathVariable("email") String email)
    {
        UserDto user=uservice.findByEmailUser(email);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/searchuserbyname/{name}")
    public  ResponseEntity<List<UserDto>> getUserByNameSearchCon(@PathVariable("name") String name)
    {
        List<UserDto> allUsers=uservice.findByNameUser(name);
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }
    @DeleteMapping("/deleteuser/{userid}")
    public ResponseEntity<ApiResponseMessage> deleteUserCon(@PathVariable("userid") String userid)
    {
        uservice.deleteUser(userid);
        ApiResponseMessage res=ApiResponseMessage.builder().message("User Deleted Successfully").
                status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
