package com.bach.Commerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "categories_blog")
@Getter
@Setter
public class CategoriesForBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    private String type;

    /*
     * @OneToMany(mappedBy = "categoriesForBlog") private List<Blog> blogs = new
     * ArrayList<>();
     */

    public CategoriesForBlog() {
    }

    public CategoriesForBlog(int id, String type) {
        super();
        this.id = id;
        this.type = type;
        //this.blogs = blogs;
    }
}
