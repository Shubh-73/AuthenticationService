package com.example.authenticationservice.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "user")
@Getter
@Setter
public class User extends BaseModel {


    String name;
    String email;
    String password;

    @ManyToMany
    private List<Role> roles;

}
