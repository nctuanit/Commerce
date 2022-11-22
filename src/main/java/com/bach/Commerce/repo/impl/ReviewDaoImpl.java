package com.bach.Commerce.repo.impl;

import com.bach.Commerce.entity.Review;
import com.bach.Commerce.repo.dao.ReviewDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@Repository
@AllArgsConstructor
public class ReviewDaoImpl implements ReviewDAO {


    final EntityManager entityManager;

    @Override
    public void add(Review review) {
        entityManager.persist(review);
    }

    @Override
    public void delete(Review review) {
        entityManager.remove(review);
    }

    @Override
    public void edit(Review review) {
        entityManager.merge(review);
    }

    @Override
    public Review getById(int id) {
        return entityManager.find(Review.class, id);
    }

    @Override
    public List<Review> find(Integer productId) {
        String jql = "select r from Review r join r.user u join r.product p where p.id =:pid";
        return entityManager.createQuery(jql, Review.class).setParameter("pid", productId).getResultList();
    }

    @Override
    public Long countById(int id) {

        String jql = "select count(r) from Review r join r.user u join r.product p where p.id =:pid";

        return entityManager.createQuery(jql, Long.class).setParameter("pid", id).getSingleResult();
    }

    @Override
    public List<Review> getAllReview() {

        String jql = "SELECT r FROM Review r";

        return entityManager.createQuery(jql, Review.class).getResultList();
    }
}
