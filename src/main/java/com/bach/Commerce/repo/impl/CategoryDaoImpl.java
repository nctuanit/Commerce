package com.bach.Commerce.repo.impl;

import com.bach.Commerce.entity.Categories;
import com.bach.Commerce.repo.dao.CategoryDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@Repository
@AllArgsConstructor
public class CategoryDaoImpl implements CategoryDAO {


    final EntityManager entityManager;

    @Override
    public List<Categories> getAll() {
        String jql = "Select c FROM Categories c";
        return entityManager.createQuery(jql, Categories.class).getResultList();
    }

}
