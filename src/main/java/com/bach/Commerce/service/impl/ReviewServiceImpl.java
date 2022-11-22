package com.bach.Commerce.service.impl;

import com.bach.Commerce.entity.Product;
import com.bach.Commerce.entity.Review;
import com.bach.Commerce.entity.User;
import com.bach.Commerce.model.dto.ProductDTO;
import com.bach.Commerce.model.dto.ReviewDTO;
import com.bach.Commerce.model.dto.UserDTO;
import com.bach.Commerce.repo.dao.ReviewDAO;
import com.bach.Commerce.service.base.ReviewService;
import com.bach.Commerce.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    final ReviewDAO reviewDao;

    @Override
    public void add(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setReviewDate(new Date());
        review.setReview(reviewDTO.getReview());
        review.setStarNumber(reviewDTO.getStarNumber());
        review.setProduct(new Product(reviewDTO.getProductDTO().getId()));
        User user = new User();
        user.setId(reviewDTO.getUserDTO().getId());
        review.setUser(user);
        reviewDao.add(review);

    }

    @Override
    public void delete(int id) {
        Review review = reviewDao.getById(id);
        if (review != null) {
            reviewDao.delete(review);
        }

    }

    @Override
    public void edit(ReviewDTO reviewDTO) {
        Review review = reviewDao.getById(reviewDTO.getId());
        if (review != null) {
            review.setStarNumber(reviewDTO.getStarNumber());
            review.setProduct(new Product(reviewDTO.getProductDTO().getId()));
            User user = new User();
            user.setName(reviewDTO.getUserDTO().getName());
            review.setUser(user);
        }
        reviewDao.edit(review);
    }

    @Override
    public ReviewDTO getById(int id) {
        Review review = reviewDao.getById(id);
        if (review != null) {
            convert(review);
        }
        return null;
    }

    private ReviewDTO convert(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setReview(review.getReview());
        reviewDTO.setReviewDate(DateTimeUtils.formatDate(review.getReviewDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
        reviewDTO.setStarNumber(review.getStarNumber());
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(review.getProduct().getId());
        productDTO.setName(review.getProduct().getName());
        reviewDTO.setProductDTO(productDTO);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(review.getUser().getId());
        userDTO.setName(review.getUser().getName());
        userDTO.setAvatar(review.getUser().getAvatar());
        reviewDTO.setUserDTO(userDTO);
        return reviewDTO;
    }

    @Override
    public List<ReviewDTO> find(int productId) {
        List<Review> reviews = reviewDao.find(productId);
        List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>();
        reviews.forEach(rev -> {
            reviewDTOs.add(convert(rev));
        });
        return reviewDTOs;
    }

    @Override
    public Long count(int productId) {

        return reviewDao.countById(productId);
    }

    @Override
    public List<ReviewDTO> getAllReview() {

        List<Review> listReview = reviewDao.getAllReview();

        List<ReviewDTO> listReviewDTO = new ArrayList<>();

        for (Review r : listReview) {

            listReviewDTO.add(convert(r));

        }

        return listReviewDTO;
    }


}
