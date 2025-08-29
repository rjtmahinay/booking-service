package com.rjtmahinay.booking.model.json;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Response object containing booking confirmation details")
public class BookingResponse {

    @Schema(description = "Status of the booking operation", example = "Success")
    private String status;
    
    @Schema(description = "Unique confirmation number for the booking", example = "A1B2C3D4")
    private String confirmationNumber;
    
    @Schema(description = "Human-readable message about the booking status", 
            example = "Booking for flight PR 123 is complete.")
    private String message;
    
    @Schema(description = "Electronic ticket number", example = "E1A2B3C4")
    private String eTicket;
}
