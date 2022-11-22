package com.bach.Commerce.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesDTO {

    private int id_cate;
    private String type;


    public CategoriesDTO() {
    }

    public CategoriesDTO(int id_cate, String type) {
        this.id_cate = id_cate;
        this.type = type;
    }

}
