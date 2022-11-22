package com.bach.Commerce.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {

    private Integer id;
    private ProductDTO product;
    private UserDTO user;
    private int quantity;

    public CartItemDTO(Integer id, ProductDTO product, UserDTO user, int quantity) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.quantity = quantity;
    }

    public CartItemDTO() {
    }
}
