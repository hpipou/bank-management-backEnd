package io.bank.management.controller;

import io.bank.management.entity.User;
import io.bank.management.repository.UserRepository;
import io.bank.management.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public String addNewUser(@RequestBody User user) {
        if(userRepository.findByEmail(user.getEmail())==null){
            userService.addNewUser(user);
            return "Compte crée avec succès";
        }else{
            return "Adresse email déjà utilisée";
        }
    }

    @PatchMapping("/users")
    public String editUser(@RequestBody User user) {

        if(userRepository.findByEmail(user.getEmail())==null){
            return "Compte introuvable";
        }else{
            userService.editUser(user);
            return "Compte mis à jour avec succès";
        }
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "Utilisateur supprimé";
    }

    @GetMapping("/users/{id}")
    public User showOneUser(@PathVariable Long id) {
        return userService.showOneUser(id);
    }

    @GetMapping("/users")
    public List<User> showAllUsers() {
        return userService.showAllUsers();
    }

}
