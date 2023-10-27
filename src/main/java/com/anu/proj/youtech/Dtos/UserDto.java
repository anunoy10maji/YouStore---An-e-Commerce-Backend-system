package com.anu.proj.youtech.Dtos;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private String userId;
    private String name;
    private String password;
    private String gender;
    private String about;
    private String email;
    private String image;
    private Set<RoleDto> roleDtos;
}
