package io.bank.management.service;

import io.bank.management.entity.UserInfo;

import java.util.List;

public interface UserInfoService {
    UserInfo addNewUserInfo(UserInfo userInfo);
    UserInfo editUserInfo(UserInfo userInfo);
    void deleteUserInfo(Long id);
    UserInfo showOnUserInfo(Long id);
    List<UserInfo> showAllUserInfo();
}
