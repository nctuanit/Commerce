package com.bach.Commerce.repo.dao;

import com.bach.Commerce.entity.CommentBlog;

import java.util.List;

public interface CommentBlogDAO {

    public List<CommentBlog> getAllComment();

    void add(CommentBlog commentBlog);

    void delete(CommentBlog commentBlog);

    public List<CommentBlog> getComment(int blogId);

    public CommentBlog getById(int id);

    public Long countById(int id);
}
