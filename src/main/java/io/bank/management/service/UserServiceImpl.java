package io.bank.management.service;

import io.bank.management.entity.User;
import io.bank.management.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User addNewUser(User user) {
        String password=user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public User editUser(User user) {
        User user1=userRepository.findByEmail(user.getEmail());
        String password=user.getPassword();
        user1.setPassword(passwordEncoder.encode(password));
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
