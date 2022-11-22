package com.bach.Commerce.repo.dao;

import com.bach.Commerce.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductDAO {

    public List<Product> getAllProduct();

    public Product getProductById(int id);

    public List<Product> getProductByCategories(int categories);

    public List<Product> getProductForProductPage(String findName, long priceStart, long priceEnd, int start, int length);

    public List<Product> findAll(String findName, String category, Pageable pageable);

    public List<Product> getProductPriceLowtoHigh(String sort);

}
