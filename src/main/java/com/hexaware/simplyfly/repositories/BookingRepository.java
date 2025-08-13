package com.hexaware.simplyfly.repositories;

import com.hexaware.simplyfly.entities.Booking;
import com.hexaware.simplyfly.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(UserInfo user);
    List<Booking> findByFlight_Id(Long flightId);

}
