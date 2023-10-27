package com.anu.proj.youtech.Services;

import com.anu.proj.youtech.Dtos.PageableResponse;
import com.anu.proj.youtech.Dtos.UserDto;
import com.anu.proj.youtech.Models.User;

import java.awt.print.Pageable;
import java.util.List;

public interface UserService {

    //Create User
    public UserDto createUser(UserDto user);
    //Get all the user
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
    //Get One Particular User
    public UserDto getUser(String id);
    //Update one user
    public UserDto updateUser(UserDto userDto,String id);
    //Find by Email
    public UserDto findByEmailUser(String email);
    //Find by Name
    public List<UserDto> findByNameUser(String name);
    //delete user
    public  void deleteUser(String id);
}
