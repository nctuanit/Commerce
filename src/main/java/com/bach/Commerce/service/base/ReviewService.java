package com.bach.Commerce.service.base;

import com.bach.Commerce.model.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getAllReview();

    void add(ReviewDTO reviewDTO);

    void delete(int id);

    void edit(ReviewDTO reviewDTO);

    ReviewDTO getById(int id);

    List<ReviewDTO> find(int id);

    public Long count(int productId);
}
