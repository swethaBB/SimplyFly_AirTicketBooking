package com.hexaware.simplyfly.services;

import com.hexaware.simplyfly.entities.UserInfo;
import java.util.List;

public interface IUserInfoService {
    UserInfo addUser(UserInfo user);
    UserInfo getUserById(Long id);
    List<UserInfo> getAllUsers();
    UserInfo updateUser(Long id, UserInfo user);
    String deleteUser(Long id);

    // Authentication helper:
    String loginAndGetToken(String email, String password);
}

