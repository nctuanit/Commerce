package com.bach.Commerce.repo.impl;

import com.bach.Commerce.entity.CommentBlog;
import com.bach.Commerce.repo.dao.CommentBlogDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@AllArgsConstructor

public class CommentBlogDaoImpl implements CommentBlogDAO {


    final EntityManager entityManager;

    @Override
    public void add(CommentBlog commentBlog) {
        entityManager.persist(commentBlog);
    }

    @Override
    public void delete(CommentBlog commentBlog) {
        entityManager.remove(commentBlog);

    }

    @Override
    public List<CommentBlog> getComment(int blogId) {
        String jql = "SELECT c FROM CommentBlog c JOIN c.blog b WHERE b.id = :bid";
        return entityManager.createQuery(jql, CommentBlog.class).setParameter("bid", blogId).getResultList();
    }

    @Override
    public List<CommentBlog> getAllComment() {
        String jql = "SELECT c FROM CommentBlog c";
        return entityManager.createQuery(jql, CommentBlog.class).getResultList();
    }

    @Override
    public CommentBlog getById(int id) {
        return entityManager.find(CommentBlog.class, id);
    }

    @Override
    public Long countById(int id) {
        String jql = "SELECT COUNT(c) FROM CommentBlog c JOIN c.blog b WHERE b.id = :bid";
        return entityManager.createQuery(jql, Long.class).setParameter("bid", id).getSingleResult();
    }

}
