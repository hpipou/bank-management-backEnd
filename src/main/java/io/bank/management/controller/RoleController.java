package io.bank.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bank.management.entity.Role;
import io.bank.management.repository.RoleRepository;
import io.bank.management.repository.UserRepository;
import io.bank.management.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class RoleController {

    private RoleService roleService;
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    public RoleController(RoleService roleService, RoleRepository roleRepository, UserRepository userRepository) {
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/roles")
    public String addNewRole(@RequestBody Role role){
        if(roleRepository.findByRoleName(role.getRoleName())==null){
            roleService.addNewRole(role);
            return "Role ajouté avec succès";
        }else{
            return "Role existe déjà";
        }
    }

    @PatchMapping("/roles")
    public void editRole(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String roleName=request.getParameter("roleName");
        Long id=Long.parseLong(request.getParameter("id"));

        if(roleService.showOneRole(id)==null){
            new ObjectMapper().writeValue(response.getOutputStream(),"Le role est introuvable");
        }else{
            roleService.editRole(id, roleName);
            new ObjectMapper().writeValue(response.getOutputStream(),"Role modifié avec succès");
        }
    }

    @DeleteMapping("/roles/{id}")
    public String deleteRole(@PathVariable Long id){

        if(roleService.showOneRole(id)==null){
            return "Le role est introuvable";
        }else{
            roleService.deleteRole(id);
            return "Role supprimé avec succès";
        }

    }

    @GetMapping("/roles/{id}")
    public Role showOneRole(@PathVariable Long id){
        return roleService.showOneRole(id);
    }

    @GetMapping("/roles")
    public List<Role> showAllRoles(){
        return roleService.showAllRoles();
    }

    @PostMapping("/roleToUser")
    public String addRoleToUser(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String roleName=request.getParameter("roleName");
        String email=request.getParameter("email");

        if(roleRepository.findByRoleName(roleName)==null){
            return "Le role est introuvable";
        }else{

            if(userRepository.findByEmail(email)==null){
                return "Le compte associé à cette adresse email est introuvable";
            }else{
                roleService.AddRoleToUser(roleName,email);
                return "Le role a été ajouté avec succès à ce compte";
            }
        }

    }

}
