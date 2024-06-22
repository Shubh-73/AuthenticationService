package com.example.authenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Token extends BaseModel{

    private String value;
    private Date expiration;

    private Boolean deleted;


    @OneToOne
    private User user;
}
