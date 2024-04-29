package com.zosh.service;


import com.zosh.model.Cart;
import com.zosh.model.CartItem;
import com.zosh.model.Food;
import com.zosh.model.User;
import com.zosh.repository.CartItemRepository;
import com.zosh.repository.CartRepository;
import com.zosh.repository.CategoryRepository;
import com.zosh.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Food food=foodService.findFoodById(req.getFoodId());

        Cart cart=cartItemRepository.findByCustomerId(user.getId());

        for(CartItem cartItem:cart.getItems())
        {
            if (cartItem.getFood().equals(food))
            {
                int newQuantity=cartItem.getQuantity()+req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }
        CartItem newcartItem=new CartItem();
        newcartItem.setFood(food);
        newcartItem.setCart(cart);
        newcartItem.setQuantity(req.getQuantity());
        newcartItem.setIngredients(req.getIngredients());
        newcartItem.setTotalPrice(req.getQuantity()* food.getPrice());

        CartItem savedCartItem=cartItemRepository.save(newcartItem);
        cart.getItems().add(savedCartItem);
    return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOptional=cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty())
        {
            throw new Exception("Cart item doesn't found");
        }
        CartItem item=cartItemOptional.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice()*quantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);

        Cart cart=cartItemRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional=cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty())
        {
            throw new Exception("Cart item doesn't found");
        }
        CartItem item=cartItemOptional.get();

        cart.getItems().remove(item);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        Long total=0L;

        for(CartItem cartItem: cart.getItems())
        {
            total+=cartItem.getFood().getPrice()* cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart=cartRepository.findById(id);
        if (optionalCart.isEmpty())
        {
            throw new Exception("Cart not  found"+id);
        }
        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
//        User user=userService.findUserByJwtToken(jwt);
        Cart cart= cartItemRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotal(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
//        User user=userService.findUserByJwtToken(jwt);
        Cart cart=findCartByUserId(userId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
