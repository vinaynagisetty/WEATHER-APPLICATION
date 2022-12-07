package com.weather.weatherbackend.controller;
import com.weather.weatherbackend.domain.WishList;
import com.weather.weatherbackend.repo.WishListRepo;
import com.weather.weatherbackend.service.WishListService;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ApiOperation(value = "This method is used to get the current date.", hidden = true)
@RequestMapping("/api/v1/wish-list")
public class WishlistController {

    @Autowired
    private WishListRepo wishListRepo;
    @Autowired
    private WishListService wishListService;

    @PostMapping(value = {"","/add"})
    public ResponseEntity addFavourite(@RequestBody WishList wishList){
        WishList save =  this.wishListRepo.save(wishList);
        return ResponseEntity.status(HttpStatus.OK).body(save);
    }
    @GetMapping(value = {"","/all"})
    public ResponseEntity getFavourits(){
        List<WishList> data = this.wishListRepo.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping(value = {"","/"})
    public ResponseEntity getFavouriteUserWish(@RequestParam String id){
        List<WishList> data = this.wishListService.userWishLists(id);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @DeleteMapping(value = {"","/del"})
    public ResponseEntity<?> delFavourite(@RequestParam Integer id,  @RequestParam String userId){
        this.wishListRepo.deleteById(id);
        List<WishList> data = this.wishListService.userWishLists(userId);
        return ResponseEntity.status(HttpStatus.OK).body(data);

    }

}
