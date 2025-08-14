package com.hexaware.simplyfly.repositories;

import com.hexaware.simplyfly.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


/*Author : Swetha 
Modified On : 27-07-2025
Description : UserInfo Repository interface 
*/

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByEmail(String email);
}
