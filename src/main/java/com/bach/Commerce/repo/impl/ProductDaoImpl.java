package com.bach.Commerce.repo.impl;

import com.bach.Commerce.entity.Product;
import com.bach.Commerce.repo.dao.ProductDAO;
import com.bach.Commerce.repo.jpa.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Repository
@AllArgsConstructor
public class ProductDaoImpl implements ProductDAO {


    final EntityManager entityManager;


    final ProductRepository productRepo;

    @Override
    public List<Product> getAllProduct() {
        String jql = "SELECT p FROM Product p";
        return entityManager.createQuery(jql, Product.class).getResultList();
    }

    @Override
    public Product getProductById(int id) {

        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> getProductByCategories(int categories) {

        String jql = "SELECT p FROM Product p WHERE p.category.id = :categories_id";

        return entityManager.createQuery(jql, Product.class).setParameter("categories_id", categories).getResultList();
    }


    @Override
    public List<Product> getProductForProductPage(String findName, long priceStart, long priceEnd, int start, int length) {

        try {
            String hql = "SELECT p FROM Product p WHERE p.name LIKE :pname and p.price between :priceStart AND :priceEnd";

            return entityManager.createQuery(hql, Product.class).setParameter("pname", "%" + findName + "%").setParameter("priceStart", priceStart).setParameter("priceEnd", priceEnd).setMaxResults(length).getResultList();

        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return null;
    }

    @Override
    public List<Product> findAll(String findName, String category, Pageable pageable) {

        return productRepo.findAll(findName, category, pageable);
    }

    @Override
    public List<Product> getProductPriceLowtoHigh(String sort) {

        List<Product> listProducts = new ArrayList<>();

        if (sort.equals("true")) {
            listProducts = productRepo.findAll(Sort.by(Sort.Direction.ASC, "price"));
            return listProducts;
        } else {
            listProducts = productRepo.findAll(Sort.by(Sort.Direction.DESC, "price"));
            return listProducts;
        }

    }
}
