package com.example.authenticationservice.controllers;


import com.example.authenticationservice.dtos.LoginRequestDto;
import com.example.authenticationservice.dtos.SignupRequestDto;
import com.example.authenticationservice.models.Token;
import com.example.authenticationservice.models.User;
import com.example.authenticationservice.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/signup")
    public User signUp(@RequestBody SignupRequestDto requestDto) throws Exception {

        String name = requestDto.getName();
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        return userAuthService.signUp(name, email, password);
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {

        String email = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        return userAuthService.login(email, password);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam("token") String token) {
        userAuthService.logout(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/validate/{token}")
    public boolean validateToken(@PathVariable("token") String token) {
        return userAuthService.validateToken(token);
    }
}
