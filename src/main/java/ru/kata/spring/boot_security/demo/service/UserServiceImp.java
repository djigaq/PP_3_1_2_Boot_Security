package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepositories;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImp implements UserService {

    private final UserRepositories userRepositories;

    @Autowired
    public UserServiceImp(UserRepositories userRepositories) {
        this.userRepositories = userRepositories;
    }

    @Override
    public List<User> getUsers() {
        return userRepositories.findAll();
    }

    @Override
    public void saveUsers(User user) {
        userRepositories.save(user);
    }


    @Override
    public User getUser(int id) {
        return userRepositories.getById(id);
    }


    @Override
    public void updateUser(User user) {
        userRepositories.save(user);
    }


    @Override
    public void deleteUser(int id) {
        userRepositories.deleteById(id);
    }

    @Override
    public User getUserByName(String name) {
        return userRepositories.getUserByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = getUserByName(name);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", name));
        }

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(u -> new SimpleGrantedAuthority(u.getRoleName())).collect(Collectors.toList());
    }
}