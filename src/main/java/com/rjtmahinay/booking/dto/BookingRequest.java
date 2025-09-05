package com.rjtmahinay.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private String flightNumber;
    
    private String origin;

    private String destination;
    
    private LocalDateTime departureTime;
    
    private LocalDateTime arrivalTime;
    
    private String passengerName;

    private String seatNumber;
}
