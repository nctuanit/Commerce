package com.bach.Commerce.service.base;

import com.bach.Commerce.model.dto.CategoriesForBlogDTO;

import java.util.List;

public interface CategoryForBlogService {

    public List<CategoriesForBlogDTO> getAllCategoryForBlog();

    public CategoriesForBlogDTO getOne(int id);

    public void add(CategoriesForBlogDTO catgoriesForBlogDTO);

    public void delete(int id);

    public void edit(CategoriesForBlogDTO categoriesForBlogDTO);

}
