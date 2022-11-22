package com.bach.Commerce.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponDTO {

    private int id;
    private String code;
    private double discount;

    public CouponDTO() {

    }

    public CouponDTO(int id, String code, double discount) {
        super();
        this.id = id;
        this.code = code;
        this.discount = discount;
    }
}
