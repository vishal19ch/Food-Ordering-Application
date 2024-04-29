package com.zosh.controller;

import com.zosh.model.Food;
import com.zosh.model.Restaurant;
import com.zosh.model.User;
import com.zosh.request.CreateFoodRequest;
import com.zosh.response.MessageResponse;
import com.zosh.service.FoodService;
import com.zosh.service.RestaurantService;
import com.zosh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private RestaurantService restaurantService;


    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);

            MessageResponse res=new MessageResponse();
            res.setMessage("food delete successfully...");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateAvailinilityStatus(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailinilityStatus(id);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
}
