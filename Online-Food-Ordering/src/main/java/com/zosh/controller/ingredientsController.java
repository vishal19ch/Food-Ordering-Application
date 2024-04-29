package com.zosh.controller;

import com.zosh.model.IngredientsCategory;
import com.zosh.model.IngredientsItem;
import com.zosh.request.IngredientsRequest;
import com.zosh.response.IngredientsCategoryRequest;
import com.zosh.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("/api/admin/ingredients")
public class ingredientsController {

    @Autowired
    private IngredientsService ingredientsService;

    @Autowired
    private IngredientsRequest ingredientsRequest;

    @PostMapping("/category")
    private ResponseEntity<IngredientsCategory> createIngredientsCategory(@RequestBody IngredientsCategoryRequest req) throws Exception {
        IngredientsCategory item=ingredientsService.createIngredientsCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }


    @PostMapping()
    private ResponseEntity<IngredientsItem> createIngredientsItem(@RequestBody IngredientsRequest req) throws Exception {
        IngredientsItem item=ingredientsService.createIngredientsItem(req.getRestaurantId(), req.getName(),req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    private ResponseEntity<IngredientsItem> updateStock(@PathVariable Long id) throws Exception {
        IngredientsItem item=ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }


    @GetMapping("/restaurant/{id}")
    private ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(@PathVariable Long id) throws Exception {
        List<IngredientsItem> items=ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }


    @GetMapping("/restaurant/{id}/category")
    private ResponseEntity<List<IngredientsCategory>> getRestaurantIngredientsCategory(@PathVariable Long id) throws Exception {
        List<IngredientsCategory> items=ingredientsService.findIngredientsCategoryByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
