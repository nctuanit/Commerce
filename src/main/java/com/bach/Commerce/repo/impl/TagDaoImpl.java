package com.bach.Commerce.repo.impl;

import com.bach.Commerce.entity.Tag;
import com.bach.Commerce.repo.dao.TagDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@AllArgsConstructor
public class TagDaoImpl implements TagDAO {


    final EntityManager entityManager;

    @Override
    public List<Tag> getAllTag() {
        String jql = "SELECT t FROM Tag t";
        return entityManager.createQuery(jql, Tag.class).getResultList();
    }


}
