package io.bank.management.service;

import io.bank.management.entity.Role;

import java.util.List;

public interface RoleService {
    Role addNewRole(Role role);
    Role editRole(Long id, String roleName);
    void deleteRole(Long id);
    Role showOneRole(Long id);
    List<Role> showAllRoles();

    void AddRoleToUser(String roleName, String email);

}
