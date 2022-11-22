package com.bach.Commerce.repo.impl;

import com.bach.Commerce.entity.User;
import com.bach.Commerce.repo.dao.UserDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class UserDaoImpl implements UserDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addUser(User u) {

        entityManager.persist(u);

    }

    @Override
    public User getUserByMail(String mail) {

        String jql = "SELECT u FROM User u WHERE u.username = :mail";

        return entityManager.createQuery(jql, User.class).setParameter("mail", mail).getSingleResult();
    }

    @Override
    public void editUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        entityManager.remove(user);
    }

    @Override
    public User getUserById(int id) {

        return entityManager.find(User.class, id);
    }
}
