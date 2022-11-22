package com.bach.Commerce.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillDTO {
    private Long id;
    private UserDTO user;
    private String buyDate;
    private Long priceTotal;
    private Integer discountPercent;
    private String status;
    private String pay;

}
