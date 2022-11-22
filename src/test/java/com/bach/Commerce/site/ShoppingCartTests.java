package com.bach.Commerce.site;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.bach.Commerce.repo.jpa.CartItemRepository;
import com.bach.Commerce.entity.CartItem;
import com.bach.Commerce.entity.Product;
import com.bach.Commerce.entity.User;


@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
@Rollback(false)
public class ShoppingCartTests {

	@Autowired
	private CartItemRepository cartRepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testAddOneCartItem() {
		Product product = entityManager.find(Product.class, 20);
		User user = entityManager.find(User.class, 32);
		
		CartItem newItem = new CartItem(); 
		//newItem.setUser(user);
		newItem.setProduct(product);
		newItem.setQuantity(2);
		
		CartItem saveCartItem = cartRepo.save(newItem);
		
		assertTrue(saveCartItem.getId() > 0);
	}
	
	
	@Test
	public void testGetCartItemByCustomer() {
		User user = new User();
		user.setId(32);
		//List<CartItem> listCartItem = cartRepo.findByUser(user);
		
		//assertEquals(2, listCartItem.size());
	}
}
