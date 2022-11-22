package com.bach.Commerce.entity;

import com.bach.Commerce.utils.NumberUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categories_id")
    private Categories category;

    @Column(name = "img_main")
    private String img_main;

    @Column(name = "img_hover")
    private String img_hover;

    @Column(name = "img_sub")
    private String img_sub;

    @Column(name = "sizes")
    private String sizes;

    @Column(name = "colors")
    private String colors;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Review> review;


    public Product() {
    }

    public Product(int id, String name, Long price, String description, Categories category,
                   String img_main, String img_hover, String img_sub) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.img_main = img_main;
        this.img_hover = img_hover;
        this.img_sub = img_sub;
    }

    public Product(int id) {
        this.id = id;
    }


    @Transient
    public String getMainImagePath() {
        if (img_main == null) return null;

        return "/product-images/" + id + "/" + img_main;
    }

    @Transient
    public String getSubImagePath() {
        if (img_sub == null) return null;

        return "/product-images/" + id + "/" + img_sub;
    }

    @Transient
    public String getHoverImagePath() {
        if (img_hover == null) return null;

        return "/product-images/" + id + "/" + img_hover;
    }

    public String toVnd(){
        return NumberUtils.toVnd(this.getPrice()) +" VND";
    }
}
