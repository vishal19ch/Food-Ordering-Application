package com.zosh.service;

import com.zosh.model.IngredientsCategory;
import com.zosh.model.IngredientsItem;
import com.zosh.model.Restaurant;
import com.zosh.repository.IngredientsCategoryRepository;
import com.zosh.repository.IngredientsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientsService{

    @Autowired
    private IngredientsItemRepository ingredientsItemRepository;

    @Autowired
    private IngredientsCategoryRepository ingredientsCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientsCategory createIngredientsCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory category=new IngredientsCategory();
        category.setRestaurant(restaurant);
        category.setName(name);

        return ingredientsCategoryRepository.save(category);
    }

    @Override
    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception {
        Optional<IngredientsCategory> opt=ingredientsCategoryRepository.findById(id);
        if(opt.isEmpty())
        {
            throw new Exception("Ingrediatent category not found");
        }
        return opt.get();
    }

    @Override
    public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientsCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String IngredientsName, Long categoryId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory category=findIngredientsCategoryById(categoryId);
        IngredientsItem item=new IngredientsItem();
        item.setName(IngredientsName);
        item.setRestaurant(restaurant);
        item.setIngredientsCategory(category);

        IngredientsItem ingredient=ingredientsItemRepository.save(item);
        category.getIngredients().add(ingredient);

        return ingredient;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {

        return ingredientsItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem=ingredientsItemRepository.findById(id);
        if(optionalIngredientsItem.isEmpty())
        {
            throw new Exception("ingredient not found..");
        }
        IngredientsItem ingredientsItem=optionalIngredientsItem.get();
        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
        return ingredientsItemRepository.save(ingredientsItem);
    }
}
