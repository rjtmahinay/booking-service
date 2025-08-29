package com.rjtmahinay.booking.model.json;

import com.rjtmahinay.booking.model.PaymentInfo;
import com.rjtmahinay.booking.model.TravelerDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request object for creating a new flight booking")
public class BookingRequest {
    @Schema(description = "Flight number for the booking", example = "PR 123", required = true)
    private String flightNumber;
    
    @Schema(description = "Airline code", example = "PR", required = true)
    private String airline;
    
    @Schema(description = "Date of departure in YYYY-MM-DD format", example = "2025-12-25", required = true)
    private String departureDate;
    
    @Schema(description = "Traveler details", required = true)
    private TravelerDetails travelerDetails;
    
    @Schema(description = "Payment information", required = true)
    private PaymentInfo paymentInfo;
}
