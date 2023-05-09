package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    void saveUsers(User user);

    User getUser(int id);

    void updateUser(User user);

    void deleteUser(int id);

    public User getUserByName(String name);
}
