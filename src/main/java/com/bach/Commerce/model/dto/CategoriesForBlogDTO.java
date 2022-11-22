package com.bach.Commerce.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesForBlogDTO {

    private int id;

    private String type;

    public CategoriesForBlogDTO() {
    }

    public CategoriesForBlogDTO(int id, String type) {
        this.id = id;
        this.type = type;
    }

}
