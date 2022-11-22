package com.bach.Commerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comment_blog")
@Getter
@Setter
public class CommentBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String comment;
    private String name;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    public CommentBlog() {
    }

    public CommentBlog(int id, String comment, String name, String email) {
        super();
        this.id = id;
        this.comment = comment;
        this.name = name;
        this.email = email;
    }


}
