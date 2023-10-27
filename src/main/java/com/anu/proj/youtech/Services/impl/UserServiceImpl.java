package com.anu.proj.youtech.Services.impl;

import com.anu.proj.youtech.Dtos.PageableResponse;
import com.anu.proj.youtech.Dtos.UserDto;
import com.anu.proj.youtech.Exceptions.ResourceNotFound;
import com.anu.proj.youtech.Helpers.Helper;
import com.anu.proj.youtech.Models.Role;
import com.anu.proj.youtech.Models.User;
import com.anu.proj.youtech.Repositories.RoleRepository;
import com.anu.proj.youtech.Repositories.UserRepository;
import com.anu.proj.youtech.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository urepo;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${normal.role.id}")
    private String role_normal_id;
    @Override
    public UserDto createUser(UserDto userDto) {
        String uid= UUID.randomUUID().toString();
        userDto.setUserId(uid);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = mapper.map(userDto,User.class);
        Role role =roleRepository.findById(role_normal_id).get();
        //System.out.println(role.getRoleName());
        user.getRoles().add(role);
        User savedUser=urepo.save(user);
        return mapper.map(savedUser, UserDto.class);
    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort s=sortDir.equalsIgnoreCase("desc")?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,s);
        Page<User> p= urepo.findAll(pageable);
        return Helper.getPageableResponse(p,UserDto.class);
    }

    @Override
    public UserDto getUser(String id) {
        User user=urepo.findById(id).orElseThrow(()->new ResourceNotFound("User not found with given id"));
        UserDto userDto=mapper.map(user,UserDto.class);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String id) {
        User user=urepo.findById(id).orElseThrow(()->new ResourceNotFound("User not found with given id"));
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setImage(userDto.getImage());
        User updatedUser=urepo.save(user);
        return mapper.map(updatedUser,UserDto.class);
    }

    @Override
    public UserDto findByEmailUser(String email) {
        User user=urepo.findByEmail(email).orElseThrow(()->new ResourceNotFound("user not found with given email"));
        return mapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> findByNameUser(String name) {
        List<User> userWithName=urepo.findByNameContaining(name);
        List<UserDto> userDtoWithName=userWithName.stream().map(user ->
                mapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtoWithName;
    }
    @Override
    public void deleteUser(String id) {
        User user=urepo.findById(id).orElseThrow(()->new ResourceNotFound("User not found with given Id"));
        urepo.delete(user);
    }
}
