package com.bach.Commerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cate")
    private int id;

    @Column(name = "type", unique = true)
    private String type;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    public Categories(int id, String type, List<Product> products) {
        this.id = id;
        this.type = type;
        this.products = products;
    }

    public Categories() {

    }
}
