package com.hexaware.simplyfly.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;


/*Author : Swetha
Modified On : 29-07-2025
Description : BookingDto with basic validation
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

/*    private Long id;


    @Positive(message = "Total price must be positive")
    private Double totalPrice;

    @Pattern(regexp = "^(PENDING|CONFIRMED|CANCELLED)$", 
             message = "Status must be PENDING, CONFIRMED, or CANCELLED")
    private String status;

    private Long userId;
    private Long flightId;
    @NotBlank
    private String seatNumber;
    @NotEmpty(message = "Seat numbers list cannot be empty")
    private List<String> seatNumbers;
    

}
*/
    private Long id;

    @Positive(message = "Total price must be positive")
    private Double totalPrice;

    @Pattern(regexp = "^(PENDING|CONFIRMED|CANCELLED)$", 
             message = "Status must be PENDING, CONFIRMED, or CANCELLED")
    private String status;

    private Long userId;
    private Long flightId;

    @NotEmpty(message = "Seat numbers list cannot be empty")
    private List<@NotBlank(message = "Seat number must not be blank") String> seatNumbers;
}