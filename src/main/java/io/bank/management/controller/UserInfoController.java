package io.bank.management.controller;

import io.bank.management.entity.UserInfo;
import io.bank.management.service.UserInfoService;
import io.bank.management.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserInfoController {

    private UserInfoService userInfoService;
    private UserService userService;

    public UserInfoController(UserInfoService userInfoService, UserService userService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
    }

    @PostMapping("/userinfos")
    public String addNewUserInfo(@RequestBody UserInfo userInfo){

        if(userService.showOneUser(userInfo.getUserID())==null){
            return "Le compte associé est introuvable";
        }else {

            if(userInfoService.showOnUserInfo(userInfo.getUserID())==null){
                userInfoService.addNewUserInfo(userInfo);
                return "Compte USERINFO crée avec succès";
            }else{
                return "Le compte USERINFO existe déja";
            }
        }

    }

    @PatchMapping("/userinfos")
    public String editUserInfo(@RequestBody UserInfo userInfo){

        if(userInfoService.showOnUserInfo(userInfo.getUserID())==null){
            return "Compte USERINFO introuvable";
        }else{
            userInfoService.editUserInfo(userInfo);
            return "Le compte USERINFO modifié avec succès";
        }

    }

    @DeleteMapping("/userinfos/{id}")
    public String deleteUserInfo(@PathVariable Long id){
        if(userInfoService.showOnUserInfo(id)==null){
            return "Le compte est introuvable";
        }else{
            userInfoService.deleteUserInfo(id);
            return "Le compte a été supprimé avec succès";
        }
    }

    @GetMapping("/userinfos/{id}")
    public UserInfo showOnUserInfo(@PathVariable Long id){
        return userInfoService.showOnUserInfo(id);
    }

    @GetMapping("/userinfos")
    public List<UserInfo> showAllUserInfo(){
        return userInfoService.showAllUserInfo();
    }


}
