package io.bank.management.service;

import io.bank.management.entity.User;

import java.util.List;

public interface UserService {
    User addNewUser(User user);
    User editUser(User user);
    void deleteUser(Long id);
    User showOneUser(Long id);
    List<User> showAllUsers();
}
