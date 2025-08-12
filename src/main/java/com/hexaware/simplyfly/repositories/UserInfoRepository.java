package com.hexaware.simplyfly.repositories;

import com.hexaware.simplyfly.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByEmail(String email);
}
