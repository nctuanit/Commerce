package com.bach.Commerce.service.impl;

import com.bach.Commerce.entity.CategoriesForBlog;
import com.bach.Commerce.model.dto.CategoriesForBlogDTO;
import com.bach.Commerce.repo.dao.CategoryForBlogDAO;
import com.bach.Commerce.service.base.CategoryForBlogService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class CategotyForBlogServiceImpl implements CategoryForBlogService {


    final CategoryForBlogDAO categoryForBlogDao;

    @Override
    public List<CategoriesForBlogDTO> getAllCategoryForBlog() {

        List<CategoriesForBlog> list = categoryForBlogDao.getAllCategoryForBlogs();

        List<CategoriesForBlogDTO> listCategoriesForBlogDTO = new ArrayList<>();

        for (CategoriesForBlog c : list) {

            CategoriesForBlogDTO categoriesForBlogDTO = new CategoriesForBlogDTO();

            categoriesForBlogDTO.setId(c.getId());
            categoriesForBlogDTO.setType(c.getType());

            listCategoriesForBlogDTO.add(categoriesForBlogDTO);

        }

        return listCategoriesForBlogDTO;
    }

    @Override
    public CategoriesForBlogDTO getOne(int id) {

        CategoriesForBlog c = categoryForBlogDao.getOne(id);

        CategoriesForBlogDTO categoriesForBlogDTO = new CategoriesForBlogDTO();

        categoriesForBlogDTO.setId(c.getId());
        categoriesForBlogDTO.setType(c.getType());

        return categoriesForBlogDTO;
    }

    @Override
    public void add(CategoriesForBlogDTO catgoriesForBlogDTO) {

        CategoriesForBlog categoriesForBlog = new CategoriesForBlog();

        categoriesForBlog.setId(catgoriesForBlogDTO.getId());
        categoriesForBlog.setType(catgoriesForBlogDTO.getType());

        categoryForBlogDao.add(categoriesForBlog);

    }

    @Override
    public void delete(int id) {

        CategoriesForBlog c = categoryForBlogDao.getOne(id);

        if (c != null) {
            categoryForBlogDao.delete(c);
        }

    }

    @Override
    public void edit(CategoriesForBlogDTO categoriesForBlogDTO) {

        CategoriesForBlog categoriesForBlog = categoryForBlogDao.getOne(categoriesForBlogDTO.getId());

        //System.out.println(categoriesForBlogDTO.getId());

        if (categoriesForBlog != null) {
            categoriesForBlog.setId(categoriesForBlogDTO.getId());
            categoriesForBlog.setType(categoriesForBlogDTO.getType());
        }

        categoryForBlogDao.edit(categoriesForBlog);


    }

}
