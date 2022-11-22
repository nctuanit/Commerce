package com.bach.Commerce.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
public class BlogDTO {

    private Integer id;

    private String title;

    private String content;

    private String created_time;

    private String imageBlog;

    private String created_month;

    private int created_day;

    private CategoriesForBlogDTO categoriesForBlogDTO;

    private List<TagDTO> tags = new ArrayList<TagDTO>(); ///cho nay em map vao giao dien ko duoc em nhe.
    /// o giao dien no la so id, con cai nay la object em ak//fix nhu nao the a
    //cach 2 don gian hon.
    // tao 1 set int luu id cua tag thoi
    private Set<Integer> tagIds = new HashSet<>();

    public BlogDTO() {
    }

    public BlogDTO(String title, String content, String created_time) {
        this.title = title;
        this.content = content;
        this.created_time = created_time;
    }

    @Transient
    public String getBlogImagePath() {
        if (imageBlog == null) return null;

        return "/blog-images/" + id + "/" + imageBlog;
    }
}
