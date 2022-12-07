package com.weather.weatherbackend.repo;
import com.weather.weatherbackend.domain.WishList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WishListRepo extends MongoRepository<WishList, Integer> {

}
