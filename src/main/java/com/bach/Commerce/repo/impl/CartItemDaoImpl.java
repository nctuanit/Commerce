package com.bach.Commerce.repo.impl;

import com.bach.Commerce.repo.dao.CartItemDAO;
import com.bach.Commerce.repo.jpa.CartItemRepository;
import com.bach.Commerce.repo.jpa.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
@AllArgsConstructor
public class CartItemDaoImpl implements CartItemDAO {


    final CartItemRepository cartItemRepo;


    final ProductRepository productRepo;

    /*
     * @Override public List<CartItem> listCartItems(User user) {
     *
     * return cartItemRepo.findByUser(user); }
     */

    /*
     * @Override public Integer addProduct(Integer productId, Integer quantity, User
     * user) {
     *
     * Integer addedQuantity = quantity;
     *
     * Product product = productRepo.findById(productId).get();
     *
     * CartItem cartItem = cartItemRepo.findByUserAndProduct(user, product);
     *
     * if(cartItem != null) { addedQuantity = cartItem.getQuantity() + quantity;
     * cartItem.setQuantity(addedQuantity); }else { cartItem = new CartItem();
     * cartItem.setQuantity(quantity); cartItem.setBill(user);
     * cartItem.setProduct(product); }
     *
     * cartItemRepo.save(cartItem);
     *
     * return addedQuantity;
     */
}
