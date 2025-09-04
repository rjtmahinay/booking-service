package com.rjtmahinay.booking.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Booking {
    
    @Id
    private String id;
    
    @Column("flight_number")
    private String flightNumber;
    
    private String origin;
    
    private String destination;
    
    @Column("departure_time")
    private LocalDateTime departureTime;
    
    @Column("arrival_time")
    private LocalDateTime arrivalTime;
    
    @Column("passenger_name")
    private String passengerName;
    
    private String status;
    
    @Column("seat_number")
    private String seatNumber;
    
    @Column("booking_date")
    @CreatedDate
    private LocalDateTime bookingDate;
    
    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    
    @Column("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    @Version
    private Long version;
}
