package com.bach.Commerce.repo.jpa;

import com.bach.Commerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    /* public List<CartItem> findByUser(User user); */

    /* public CartItem findByUserAndProduct(User user, Product product); */

}
