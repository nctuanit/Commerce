package com.bach.Commerce.repo.dao;

import com.bach.Commerce.entity.CategoriesForBlog;

import java.util.List;

public interface CategoryForBlogDAO {

    public List<CategoriesForBlog> getAllCategoryForBlogs();

    public CategoriesForBlog getOne(int id);

    public void add(CategoriesForBlog categoriesForBlog);

    public void delete(CategoriesForBlog categoriesForBlog);

    public void edit(CategoriesForBlog categoriesForBlog);

}
