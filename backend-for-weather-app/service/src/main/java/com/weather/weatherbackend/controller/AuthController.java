package com.weather.weatherbackend.controller;

import com.weather.weatherbackend.domain.User;
import com.weather.weatherbackend.repo.UserRepo;
import com.weather.weatherbackend.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import com.nutritionistapp.nutritionistbackend.domain.User;
//import com.nutritionistapp.nutritionistbackend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@ApiOperation(value = "This method is used to get the current date.", hidden = true)
@RequestMapping("/api/v1/users")
public class AuthController {

    private UserService userService;
    private UserRepo userRepo;
    @Autowired
    public AuthController(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    //    public AuthController(UserService userService) {
//        this.userService = userService;
//    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user){
        Map<String, String> resposne = new HashMap<>();
        Optional<User> login = userService.login(user.getUserName(), user.getPassword());
        if(!login.isPresent()) {
            resposne.put("message", "Invalid Credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        System.out.println(login);
        // send JWT when login is successful
        String token = userService.getToken(user);
        resposne.put("token", token);
        resposne.put("name", login.get().getName());
        resposne.put("id", String.valueOf(login.get().getId()));
        resposne.put("status", HttpStatus.OK.toString());
        return ResponseEntity.status(HttpStatus.OK).body(resposne);
    }
    @PutMapping("/profile-update")
    public ResponseEntity<User> update(@RequestBody User user, @RequestParam Integer id){
        Optional<User> data = userRepo.findById(id);
        User m = new User(data.get().getId(), data.get().getUserName(), data.get().getPassword(),user.getName(), user.getPhone());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(m));

    }
    @ApiOperation(value = "This method is used to get the current date.", hidden = true)
    @GetMapping("/profile-update-pass")
    public ResponseEntity<User> updatePass(@RequestParam Integer id, @RequestParam String pass){
        Optional<User> data = userRepo.findById(id);
        User mm  = new User(data.get().getId(), data.get().getUserName(), pass,data.get().getName(), data.get().getPhone());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(mm));

    }

    @ApiOperation(value = "This method is used to get the current date.", hidden = true)
    @GetMapping(value={"", "/"})
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        if(allUsers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }


    @ApiOperation(value = "This method is used to get the current date.", hidden = true)
    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestHeader("Authorization") String token) {
        Map<String, String> response = new HashMap<>();
        if( token.isEmpty()) {
            response.put("message", "Invalid token");
            response.put("status", HttpStatus.UNAUTHORIZED.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        if(!userService.validateToken(token.substring(7))) {
            response.put("message", "Invalid token");
            response.put("status", HttpStatus.UNAUTHORIZED.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        response.put("message", "Valid token");
        response.put("status", HttpStatus.OK.toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
