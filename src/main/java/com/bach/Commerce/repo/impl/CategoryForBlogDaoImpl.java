package com.bach.Commerce.repo.impl;

import com.bach.Commerce.entity.CategoriesForBlog;
import com.bach.Commerce.repo.dao.CategoryForBlogDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@AllArgsConstructor
public class CategoryForBlogDaoImpl implements CategoryForBlogDAO {


    final EntityManager entityManager;

    @Override
    public List<CategoriesForBlog> getAllCategoryForBlogs() {
        String jql = "Select c FROM CategoriesForBlog c";
        return entityManager.createQuery(jql, CategoriesForBlog.class).getResultList();
    }

    @Override
    public CategoriesForBlog getOne(int id) {

        return entityManager.find(CategoriesForBlog.class, id);
    }

    @Override
    public void add(CategoriesForBlog categoriesForBlog) {
        entityManager.persist(categoriesForBlog);
    }

    @Override
    public void delete(CategoriesForBlog categoriesForBlog) {
        entityManager.remove(categoriesForBlog);
    }

    @Override
    public void edit(CategoriesForBlog categoriesForBlog) {
        entityManager.merge(categoriesForBlog);
    }


}
