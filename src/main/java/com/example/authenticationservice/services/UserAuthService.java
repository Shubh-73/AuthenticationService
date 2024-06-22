package com.example.authenticationservice.services;


import com.example.authenticationservice.models.Token;
import com.example.authenticationservice.models.User;
import com.example.authenticationservice.repositories.TokenRepository;
import com.example.authenticationservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAuthService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TokenRepository tokenRepository;

    public User signUp(String username, String email, String password) throws Exception {
        //return null;
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) throw new Exception("User already present");
        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    public Token login(String username, String password) throws Exception {

        Optional<User> userOptional = userRepository.findByEmail(username);
        if(!userOptional.isPresent()) throw new Exception("User not found");
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Wrong password");
        }
        Token token = new Token();
        token.setUser(user);
        token.setExpiration(get30DaysLaterDate());
        token.setValue(UUID.randomUUID().toString());

        return tokenRepository.save(token);


    }

    private Date get30DaysLaterDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        return calendar.getTime();
    }



    public void logout(String token) {


        //first, find out if the token is valid.
        //need to make sure if the token is deleted

        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeleted(token, false);


        if(tokenOptional.isEmpty()){
            return;
        }

        Token tokenCurrent = tokenOptional.get();
        tokenCurrent.setDeleted(true);
        tokenRepository.save(tokenCurrent);
    }

    public boolean validateToken(String token){

        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedAndExpirationGreaterThan(
                token, false, new Date());


        return tokenOptional.isPresent();
    }
}
