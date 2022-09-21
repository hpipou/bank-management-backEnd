package io.bank.management.service;

import io.bank.management.entity.Role;
import io.bank.management.entity.User;
import io.bank.management.repository.RoleRepository;
import io.bank.management.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role editRole(Long id, String roleName) {
        Role role1=roleRepository.findById(id).orElse(null);
        role1.setRoleName(roleName);
        return roleRepository.save(role1);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role showOneRole(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Role> showAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void AddRoleToUser(String roleName, String email) {
        User user= userRepository.findByEmail(email);
        Role role= roleRepository.findByRoleName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

}
