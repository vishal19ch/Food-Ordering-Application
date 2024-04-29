package com.zosh.service;

import com.zosh.model.IngredientsCategory;
import com.zosh.model.IngredientsItem;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IngredientsService  {

    public IngredientsCategory createIngredientsCategory(String name,Long restaurantId) throws Exception;

    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception;

    public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception;

    public IngredientsItem createIngredientsItem(Long restaurantId,String IngredientsName,Long category) throws Exception;

    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId);

    public IngredientsItem updateStock(Long id) throws Exception;
}
