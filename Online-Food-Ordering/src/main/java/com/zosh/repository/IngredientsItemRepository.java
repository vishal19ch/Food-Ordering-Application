package com.zosh.repository;

import com.zosh.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsItemRepository extends JpaRepository<IngredientsItem,Long> {

    List<IngredientsItem> findByRestaurantId(Long restaurantId);
}
