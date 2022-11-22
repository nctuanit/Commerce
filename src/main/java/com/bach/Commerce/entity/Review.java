package com.bach.Commerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "review")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "review")
    private String review;

    @Column(name = "star_number")
    private Integer starNumber;

    @Column(name = "review_date")
    private Date reviewDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Review(int id, String review, Integer starNumber, Date reviewDate, User user, Product product) {
        super();
        this.id = id;
        this.starNumber = starNumber;
        this.reviewDate = reviewDate;
        this.user = user;
        this.product = product;
    }

    public Review(int id) {
        super();
        this.id = id;
    }

    public Review() {

    }


}
