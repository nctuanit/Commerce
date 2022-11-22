package com.bach.Commerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    private int quantity;

    public CartItem() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CartItem(Integer id, Product product, int quantity) {
        super();
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }


}
