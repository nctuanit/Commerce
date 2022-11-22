package com.bach.Commerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bill")
@Getter
@Setter
public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "buy_date")
    private Date buyDate;

    @Column(name = "price_total")
    private Long priceTotal;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @Column(name = "status")
    private String status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bill")
    private List<CartItem> cartItem;

    @Column(name = "pay")
    private String pay;

}