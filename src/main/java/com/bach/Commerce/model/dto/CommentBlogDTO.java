package com.bach.Commerce.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentBlogDTO {
    private int id;
    private String comment;
    private String name;
    private String email;
    private BlogDTO blogDTO;

    public CommentBlogDTO() {

    }

    public CommentBlogDTO(int id, String comment, String name, String email, BlogDTO blogDTO) {
        super();
        this.id = id;
        this.comment = comment;
        this.name = name;
        this.email = email;
        this.blogDTO = blogDTO;
    }
}
