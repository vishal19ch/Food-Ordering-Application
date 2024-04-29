package com.zosh.service;

import com.zosh.model.Category;
import com.zosh.model.Food;
import com.zosh.model.Restaurant;
import com.zosh.repository.FoodRepository;
import com.zosh.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
       Food food=new Food();
       food.setFoodCategory(category);
       food.setRestaurant(restaurant);
       food.setDescription(req.getDescription());
       food.setImages(req.getImages());
       food.setPrice(req.getPrice());
       food.setIngredients(req.getIngredients());
       food.setSeasonal(req.isSessional());
       food.setVegetarian(req.isVegetarian());
       return foodRepository.save(food);
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
            Food food=findFoodById(foodId);
            food.setRestaurant(null);
            foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isvegitarian, boolean isNonveg, boolean isSeasonal, String foodCategory) {

        List<Food> foods=foodRepository.findByRestaurantId(restaurantId);
        if(isvegitarian)
        {
           foods=filterByVegietarian(foods,isvegitarian);
        }
        if(isNonveg)
        {
            foods=filterByNonVeg(foods,isNonveg);
        }
        if(isSeasonal)
        {
            foods=filterBySeasonal(foods,isSeasonal);
        }
        if(foodCategory!=null && !foodCategory.equals(""))
        {
            foods=filterByCategory(foods,foodCategory);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory() != null
            ) {

                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> food.isVegetarian()==false).collect(Collectors.toList());
    }

    private List<Food> filterByVegietarian(List<Food> foods, boolean isvegitarian) {
        return foods.stream().filter(food -> food.isVegetarian()==isvegitarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood=foodRepository.findById(foodId);
        if(optionalFood.isEmpty())
        {
            throw new Exception("food not exist..");
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailinilityStatus(Long foodId) throws Exception {
        Food food=findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
