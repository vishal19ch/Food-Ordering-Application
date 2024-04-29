package com.zosh.controller;

import com.zosh.model.Order;
import com.zosh.model.User;
import com.zosh.request.OrderRequest;
import com.zosh.service.OrderService;
import com.zosh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller

@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long id,
                                                       @RequestParam(required = false) String order_Status,@RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getRestaurantOrder(id,order_Status);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id,@PathVariable String orderStatus,@RequestParam(required = false) String order_Status,@RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Order orders = orderService.updateOrder(id,orderStatus);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }



}
