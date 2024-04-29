package com.zosh.service;


import com.zosh.model.*;
import com.zosh.repository.*;
import com.zosh.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired

    private  CartService cartService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {

        Address shippingAddress=order.getDelivery();
        Address savedAddress=addressRepository.save(shippingAddress);

        if(!user.getAddresses().contains((savedAddress)))
        {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }
        Restaurant restaurant=restaurantService.findRestaurantById(order.getRestaurantId());
        Order createdOrder=new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderstatus("Pending");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);
        Cart cart=cartService.findCartByUserId(user.getId());

        List<OrderItems> orderItems=new ArrayList<>();

        for (CartItem cartItem: cart.getItems())
        {
            OrderItems orderItems1=new OrderItems();
            orderItems1.setFood(cartItem.getFood());
            orderItems1.setQuantity(cartItem.getQuantity());
            orderItems1.setIngredients(cartItem.getIngredients());
            orderItems1.setTotalPrice(cartItem.getTotalPrice());

            OrderItems savedOrderItem=orderItemRepository.save(orderItems1);
            orderItems.add(savedOrderItem);
        }
        Long totalPrice=cartService.calculateCartTotal(cart);

        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder=orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order=findOrderById(orderId);
        if (orderStatus.equals("OUT_OF_DELIVERY") || orderStatus.equals("DELIVERED") || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING"))
        {
            order.setOrderstatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please Selected a vaild order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order=findOrderById(orderId);
        orderRepository.deleteById(orderId);

    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {

        return orderRepository.findByRestaurantId(userId);
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders= orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus!=null)
        {
            orders=orders.stream().filter(order -> order.getOrderstatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder=orderRepository.findById(orderId);
                if(optionalOrder.isEmpty())
                {
                    throw new Exception("order not found..");
                }
        return optionalOrder.get();
    }
}
