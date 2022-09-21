package io.bank.management.service;

import io.bank.management.entity.User;
import io.bank.management.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addNewUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User editUser(User user) {
        User user1=userRepository.findByEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        return userRepository.save(user1);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User showOneUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> showAllUsers() {
        return userRepository.findAll();
    }
}
