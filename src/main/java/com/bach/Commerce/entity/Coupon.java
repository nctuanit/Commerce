package com.bach.Commerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "coupon")
@Getter
@Setter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String code;
    private int discount;

    public Coupon() {

    }

    public Coupon(int id, String code, int discount) {
        super();
        this.id = id;
        this.code = code;
        this.discount = discount;
    }


}
