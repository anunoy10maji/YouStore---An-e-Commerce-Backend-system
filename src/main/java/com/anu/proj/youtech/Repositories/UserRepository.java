package com.anu.proj.youtech.Repositories;

import com.anu.proj.youtech.Dtos.UserDto;
import com.anu.proj.youtech.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    public Optional<User> findByEmail(String email);
    public List<User> findByNameContaining(String name);
}
