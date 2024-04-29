package com.zosh.controller;

import com.zosh.model.Restaurant;
import com.zosh.model.User;
import com.zosh.request.CreateResturantRequest;
import com.zosh.response.MessageResponse;
import com.zosh.service.RestaurantService;
import com.zosh.service.UserService;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@Controller
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateResturantRequest req,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Restaurant restaurant=restaurantService.createRestaurant(req,user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateResturantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Restaurant restaurant=restaurantService.updateRestaurant(id,req);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestBody CreateResturantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        restaurantService.deleteRestaurant(id);
        MessageResponse res=new MessageResponse();
        res.setMessage("restaurant deleted successfully...");
        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }


    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestBody CreateResturantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestBody CreateResturantRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


}
