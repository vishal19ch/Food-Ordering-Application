package com.zosh.request;


import lombok.Data;

@Data
public class UpdateCartItemRequest {

    private Long carItemId;

    private int quantity;
}
