package com.bach.Commerce.service.base;

import com.bach.Commerce.model.dto.CommentBlogDTO;

import java.util.List;

public interface CommentBlogService {

    public List<CommentBlogDTO> getAll();

    void delete(int id);

    void add(CommentBlogDTO commentBlogDTO);

    public List<CommentBlogDTO> getComment(int id);

    public CommentBlogDTO getById(int id);

}
