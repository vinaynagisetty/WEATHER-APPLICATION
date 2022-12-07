package com.weather.weatherbackend.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.weather.weatherbackend.domain.User;
import com.weather.weatherbackend.repo.UserRepo;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User register(User user) {
        return userRepo.save(user);
    }

    @Override
    public Optional<User> login(String userName, String password) {
        return userRepo.findByUserNameAndPassword(userName, password);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public String getToken(User user) {
        String token = null;
        token = Jwts
                .builder().setSubject(user.getUserName()).claim("user", user.getUserName())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "myPassword").compact();
        return token;
    }

    @Override
    public boolean validateToken(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey("myPassword")
                .parseClaimsJws(token)
                .getBody();
        if (claims != null) {
            return true;
        }
        return false;
    }
}
