package com.bach.Commerce.model.dto;

import antlr.StringUtils;
import com.bach.Commerce.utils.NumberUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;


@Getter
@Setter
public class ProductDTO {

    private int id;
    private String name;
    private Long price;
    private String description;
    private CategoriesDTO category;
    private String img_main;
    private String img_hover;
    private String img_sub;

    public ProductDTO() {
    }

    public ProductDTO(int id, String name, Long price, String description, CategoriesDTO category, String img_main,
                      String img_hover, String img_sub) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.img_main = img_main;
        this.img_hover = img_hover;
        this.img_sub = img_sub;
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
        return NumberUtils.toVnd(this.getPrice()) +" đ";
    }
}
