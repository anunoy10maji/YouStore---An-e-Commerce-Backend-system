package com.anu.proj.youtech.Controller;

import com.anu.proj.youtech.Dtos.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    ModelMapper mapper;
    @GetMapping("/currentUser")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal)
    {
        String name= principal.getName();
        return new ResponseEntity<>(mapper.map(userDetailsService.loadUserByUsername(name), UserDto.class), HttpStatus.OK);
    }
}
