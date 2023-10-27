package com.anu.proj.youtech.Repositories;

import com.anu.proj.youtech.Models.Role;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {




}
