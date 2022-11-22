package com.bach.Commerce.repo.dao;

import com.bach.Commerce.entity.Review;

import java.util.List;

public interface ReviewDAO {

    List<Review> getAllReview();

    void add(Review review);

    void delete(Review review);

    void edit(Review review);

    Review getById(int id);

    List<Review> find(Integer productId);

    Long countById(int id);
}
