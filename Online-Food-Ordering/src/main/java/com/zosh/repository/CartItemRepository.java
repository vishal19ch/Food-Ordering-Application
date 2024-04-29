package com.zosh.repository;

import com.zosh.model.Cart;
import com.zosh.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
     public Cart findByCustomerId(Long userId) throws Exception;

}
