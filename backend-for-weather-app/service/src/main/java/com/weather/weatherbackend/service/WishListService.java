package com.weather.weatherbackend.service;

import com.weather.weatherbackend.domain.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class WishListService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public WishListService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<WishList> userWishLists(String userId){
       Query query = new Query().addCriteria(Criteria.where("user_id").is(userId));
       return mongoTemplate.find(query, WishList.class);
    }
}
