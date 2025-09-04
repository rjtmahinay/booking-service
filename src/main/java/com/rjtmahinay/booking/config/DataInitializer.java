package com.rjtmahinay.booking.config;

import com.rjtmahinay.booking.entity.Booking;
import com.rjtmahinay.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Profile("!test") // Don't run in tests
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final BookingRepository bookingRepository;

    @Override
    public void run(String... args) {
        log.info("Starting data initialization...");
        
        // First, try to delete existing data if the table exists
        bookingRepository.count()
            .flatMap(count -> {
                log.info("Found {} existing bookings, deleting...", count);
                return bookingRepository.deleteAll();
            })
            .onErrorResume(error -> {
                log.info("No existing bookings found, proceeding with initialization");
                return Mono.empty();
            })
            .thenMany(
                Flux.just(
                    createBooking("BKG001", "AA123", "JFK", "LAX", 
                        LocalDateTime.of(2025, 10, 15, 8, 30), 
                        LocalDateTime.of(2025, 10, 15, 11, 45), 
                        "John Doe", "CONFIRMED", "12A", 
                        LocalDateTime.now().withNano(0)),
                    createBooking("BKG002", "DL456", "LAX", "JFK", 
                        LocalDateTime.of(2025, 11, 20, 15, 45), 
                        LocalDateTime.of(2025, 11, 20, 23, 15), 
                        "Jane Smith", "PENDING", "24B", 
                        LocalDateTime.now().withNano(0)),
                    createBooking("BKG003", "UA789", "ORD", "SFO", 
                        LocalDateTime.of(2025, 12, 5, 10, 0), 
                        LocalDateTime.of(2025, 12, 5, 12, 30), 
                        "Robert Johnson", "CANCELLED", "08C", 
                        LocalDateTime.now().withNano(0))
                )
                .flatMap(bookingRepository::save)
            )
            .thenMany(bookingRepository.findAll())
            .subscribe(
                booking -> log.info("Inserted booking: {}", booking.getId()),
                error -> log.error("Error initializing data: {}", error.getMessage(), error),
                () -> log.info("Data initialization completed successfully")
            );
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
