package com.bach.Commerce.service.impl;

import com.bach.Commerce.entity.Blog;
import com.bach.Commerce.entity.CommentBlog;
import com.bach.Commerce.model.dto.BlogDTO;
import com.bach.Commerce.model.dto.CommentBlogDTO;
import com.bach.Commerce.repo.dao.CommentBlogDAO;
import com.bach.Commerce.service.base.CommentBlogService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CommentBlogServiceImpl implements CommentBlogService {

    final CommentBlogDAO commentBlogDao;

    private CommentBlogDTO convert(CommentBlog cb) {
        CommentBlogDTO commentBlogDTO = new CommentBlogDTO();

        commentBlogDTO.setId(cb.getId());
        commentBlogDTO.setName(cb.getName());
        commentBlogDTO.setEmail(cb.getEmail());
        commentBlogDTO.setComment(cb.getComment());

        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setId(cb.getBlog().getId());
        blogDTO.setTitle(cb.getBlog().getTitle());

        commentBlogDTO.setBlogDTO(blogDTO);

        return commentBlogDTO;
    }

    @Override
    public List<CommentBlogDTO> getAll() {

        List<CommentBlog> listComments = commentBlogDao.getAllComment();

        List<CommentBlogDTO> listCommentDTO = new ArrayList<>();

        for (CommentBlog cb : listComments) {

            listCommentDTO.add(convert(cb));

        }

        return listCommentDTO;
    }

    @Override
    public void delete(int id) {
        CommentBlog commentBlog = commentBlogDao.getById(id);

        if (commentBlog != null) {
            commentBlogDao.delete(commentBlog);
        }
    }

    @Override
    public void add(CommentBlogDTO commentBlogDTO) {

        CommentBlog commentBlog = new CommentBlog();

        commentBlog.setId(commentBlogDTO.getId());
        commentBlog.setComment(commentBlogDTO.getComment());
        commentBlog.setEmail(commentBlogDTO.getEmail());
        commentBlog.setName(commentBlogDTO.getName());
        commentBlog.setBlog(new Blog(commentBlogDTO.getBlogDTO().getId()));

        commentBlogDao.add(commentBlog);

    }

    @Override
    public List<CommentBlogDTO> getComment(int id) {

        List<CommentBlog> listCommentBlogs = commentBlogDao.getComment(id);

        List<CommentBlogDTO> listCommentBlogDTOs = new ArrayList<>();

        for (CommentBlog cb : listCommentBlogs) {

            listCommentBlogDTOs.add(convert(cb));

        }

        return listCommentBlogDTOs;
    }

    @Override
    public CommentBlogDTO getById(int id) {
        CommentBlog commentBlog = commentBlogDao.getById(id);

        if (commentBlog != null) {
            convert(commentBlog);
        }

        return null;
    }

}
