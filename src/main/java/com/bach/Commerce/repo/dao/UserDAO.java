package com.bach.Commerce.repo.dao;

import com.bach.Commerce.entity.User;

public interface UserDAO {

    public void addUser(User p);

    public User getUserByMail(String mail);

    public void editUser(User user);

    public void deleteUser(User user);

    public User getUserById(int id);
}
