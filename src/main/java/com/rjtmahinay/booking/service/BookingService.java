package com.rjtmahinay.booking.service;

import com.rjtmahinay.booking.entity.Booking;
import com.rjtmahinay.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;

    public Mono<Booking> createBooking(Booking booking) {
        return bookingRepository.save(booking)
                .doOnError(error -> log.error("Error creating booking: {}", error.getMessage()));
    }

    public Flux<Booking> getAllBookings() {
        return bookingRepository.findAll()
                .doOnError(error -> log.error("Error fetching all bookings: {}", error.getMessage()));
    }

    public Mono<Booking> getBookingById(String id) {
        return bookingRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Booking not found with id: " + id)))
                .doOnError(error -> log.error("Error fetching booking with id {}: {}", id, error.getMessage()))
                .doOnError(error -> log.error("Error deleting booking with id {}: {}", id, error.getMessage()));
    }

    public Mono<Void> deleteBooking(String id) {
        return bookingRepository.deleteById(id)
                .doOnError(error -> log.error("Error deleting booking with id {}: {}", id, error.getMessage()));
    }

    private Booking createBooking(String id, String flightNumber, String origin, String destination,
                                LocalDateTime departureTime, LocalDateTime arrivalTime,
                                String passengerName, String status, String seatNumber, 
                                LocalDateTime bookingDate) {
        return Booking.builder()
                .id(id)
                .flightNumber(flightNumber)
                .origin(origin)
                .destination(destination)
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .passengerName(passengerName)
                .status(status)
                .seatNumber(seatNumber)
                .bookingDate(bookingDate)
                .build();
    }
}
