package com.zosh.service;


import com.zosh.model.Order;
import com.zosh.model.User;
import com.zosh.request.OrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {

    public Order createOrder(OrderRequest order, User user) throws Exception;

    public Order updateOrder(Long orderId,String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUserOrder(Long userId) throws Exception;

    public List<Order> getRestaurantOrder(Long restaurantId,String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;


}
