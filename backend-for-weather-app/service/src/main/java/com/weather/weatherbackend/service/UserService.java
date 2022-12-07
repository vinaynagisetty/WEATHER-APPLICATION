package com.weather.weatherbackend.service;


import java.util.List;
import java.util.Optional;
import com.weather.weatherbackend.domain.User;

public interface UserService {

    User register(User user);

    Optional<User> login(String userName, String password);

    List<User> getAllUsers();

    String getToken(User user);

    boolean validateToken(String token);
}
