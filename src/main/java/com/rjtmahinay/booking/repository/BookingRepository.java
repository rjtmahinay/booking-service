package com.rjtmahinay.booking.repository;

import com.rjtmahinay.booking.entity.Booking;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends R2dbcRepository<Booking, String> {
    // Add custom reactive query methods here if needed
}
