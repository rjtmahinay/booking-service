package com.rjtmahinay.booking.mapper;

import com.rjtmahinay.booking.dto.BookingResponse;
import com.rjtmahinay.booking.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingResponse toDto(Booking booking) {
        if (booking == null) {
            return null;
        }

        return BookingResponse.builder()
                .id(booking.getId())
                .flightNumber(booking.getFlightNumber())
                .origin(booking.getOrigin())
                .destination(booking.getDestination())
                .departureTime(booking.getDepartureTime())
                .arrivalTime(booking.getArrivalTime())
                .passengerName(booking.getPassengerName())
                .status(booking.getStatus())
                .seatNumber(booking.getSeatNumber())
                .bookingDate(booking.getBookingDate())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }
}
