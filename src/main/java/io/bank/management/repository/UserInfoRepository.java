package io.bank.management.repository;

import io.bank.management.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    UserInfo findByUserID(Long id);
}
