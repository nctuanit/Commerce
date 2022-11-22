package com.bach.Commerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "blog")
@Getter
@Setter
public class Blog {

    @Column(name = "image")
    public String imageBlog;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "created_time", updatable = false)
    private String created_time;

    @Column(name = "created_month", updatable = false)
    private String created_month;

    @Column(name = "created_day", updatable = false)
    private int created_day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoriesForBlog categoriesForBlog;

    //many to many dung dung casecade nha. vi no se luu auto theo roi.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "blog_tag", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<Tag>();

    public Blog() {
    }

    public Blog(String title, String content, String created_time) {
        this.title = title;
        this.content = content;
        this.created_time = created_time;
    }

    public Blog(Integer id, String title, String content, String created_time, String created_month, int created_day,
                CategoriesForBlog categoriesForBlog, Set<Tag> tags, String imageBlog) {
        super();
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageBlog = imageBlog;
        this.created_time = created_time;
        this.created_month = created_month;
        this.created_day = created_day;
        this.categoriesForBlog = categoriesForBlog;
        this.tags = tags;
    }

    public Blog(Integer id2) {
        this.id = id2;
    }


    @Transient
    public String getBlogImagePath() {
        if (imageBlog == null)
            return null;

        return "/blog-images/" + id + "/" + imageBlog;
    }

}
