package com.rjtmahinay.booking.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Traveler's personal information")
public class TravelerDetails {
    @Schema(description = "Full name of the traveler", example = "John Doe", required = true)
    private String fullName;
    
    @Schema(description = "Email address of the traveler", example = "john.doe@example.com", required = true)
    private String email;
    
    @Schema(description = "Contact phone number", example = "+1234567890", required = true)
    private String phone;
}
