package com.zosh.response;

import lombok.Data;

@Data
public class IngredientsCategoryRequest {
    private String name;
    private Long restaurantId;

}
