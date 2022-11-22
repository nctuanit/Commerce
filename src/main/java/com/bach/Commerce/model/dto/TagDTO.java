package com.bach.Commerce.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDTO {

    private int id;
    private String type;

    public TagDTO() {

    }

    public TagDTO(int id, String type) {
        super();
        this.id = id;
        this.type = type;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
}
