package com.bach.Commerce.service.impl;

import com.bach.Commerce.entity.Categories;
import com.bach.Commerce.model.dto.CategoriesDTO;
import com.bach.Commerce.repo.dao.CategoryDAO;
import com.bach.Commerce.service.base.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    final CategoryDAO categoryDAO;

    @Override
    public List<CategoriesDTO> getAll() {

        List<Categories> entityCategory = categoryDAO.getAll();

        List<CategoriesDTO> listCategory = new ArrayList<CategoriesDTO>();

        for (Categories cate : entityCategory) {

            CategoriesDTO categoryDTO = new CategoriesDTO();

            categoryDTO.setId_cate(cate.getId());

            categoryDTO.setType(cate.getType());

            listCategory.add(categoryDTO);

        }

        return listCategory;
    }

}
