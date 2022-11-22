package com.bach.Commerce.repo.impl;

import com.bach.Commerce.entity.Blog;
import com.bach.Commerce.repo.dao.BlogDAO;
import com.bach.Commerce.repo.jpa.BlogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;


@Transactional
@Repository
@AllArgsConstructor
public class BlogDaoImpl implements BlogDAO {
    final EntityManager enityManger;
    final BlogRepository blogRepo;

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepo.findAll();
    }

    @Override
    public Blog getOneBlog(Integer id) {
        return blogRepo.getById(id);
    }

    @Override
    public void addNewBlog(Blog blog) {
        enityManger.persist(blog);
    }

    @Override
    public void deleteBlog(Blog blog) {
        enityManger.remove(blog);
    }

    @Override
    public void editBlog(Blog blog) {
        enityManger.merge(blog);
    }

    /*
     * @Override public List<Blog> getBlogForBlogPage(String keyword, String
     * categoryForBlog, String month, Pageable pageable) {
     *
     * return blogRepo.findAll(keyword, categoryForBlog, month, pageable); }
     */
}
