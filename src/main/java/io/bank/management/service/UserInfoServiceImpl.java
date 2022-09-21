package io.bank.management.service;

import io.bank.management.entity.UserInfo;
import io.bank.management.repository.UserInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepository userInfoRepository;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserInfo addNewUserInfo(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo editUserInfo(UserInfo userInfo) {
        UserInfo userInfo1=userInfoRepository.findByUserID(userInfo.getUserID());

        userInfo1.setFirstName(userInfo.getFirstName());
        userInfo1.setLastName(userInfo.getLastName());
        userInfo1.setPhone(userInfo.getPhone());
        userInfo1.setAdresse(userInfo.getAdresse());

        return userInfoRepository.save(userInfo1);
    }

    @Override
    public void deleteUserInfo(Long id) {
        UserInfo userInfo=userInfoRepository.findByUserID(id);
        userInfoRepository.deleteById(userInfo.getId());
    }

    @Override
    public UserInfo showOnUserInfo(Long id) {
        return userInfoRepository.findByUserID(id);

    }

    @Override
    public List<UserInfo> showAllUserInfo() {
        return userInfoRepository.findAll();
    }
}
