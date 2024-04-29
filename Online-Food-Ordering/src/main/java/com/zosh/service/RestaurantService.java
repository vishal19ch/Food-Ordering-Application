package com.zosh.service;

import com.zosh.dto.RestaurantDto;
import com.zosh.model.Restaurant;
import com.zosh.model.User;
import com.zosh.request.CreateResturantRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateResturantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId,CreateResturantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws  Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId,User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;


}
