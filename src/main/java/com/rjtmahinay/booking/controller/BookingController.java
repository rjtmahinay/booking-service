package com.rjtmahinay.booking.controller;

import com.rjtmahinay.booking.dto.BookingRequest;
import com.rjtmahinay.booking.dto.BookingResponse;
import com.rjtmahinay.booking.entity.Booking;
import com.rjtmahinay.booking.mapper.BookingMapper;
import com.rjtmahinay.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking Controller", description = "APIs for flight booking operations")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new booking", description = "Creates a new flight booking with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created successfully",
                    content = @Content(schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public Mono<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        // Convert DTO to entity
        Booking booking = Booking.builder()
                .id("BKG" + java.util.UUID.randomUUID().toString().substring(0, 5).toUpperCase())
                .flightNumber(request.getFlightNumber())
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .passengerName(request.getPassengerName())
                .status("CONFIRMED")
                .seatNumber(request.getSeatNumber())
                .bookingDate(java.time.LocalDateTime.now())
                .build();

        // Save booking and map to DTO
        return bookingService.createBooking(booking)
                .map(bookingMapper::toDto)
                .doOnError(e -> log.error("Error creating booking", e));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID", description = "Retrieves booking details by booking ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found",
                    content = @Content(schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public Mono<BookingResponse> getBookingById(@PathVariable String id) {
        return bookingService.getBookingById(id)
                .map(bookingMapper::toDto)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found with id: " + id)));
    }

    @GetMapping
    @Operation(summary = "Get all bookings", description = "Retrieves a list of all bookings")
    public Flux<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings()
                .map(bookingMapper::toDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a booking", description = "Deletes a booking by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public Mono<Void> deleteBooking(@PathVariable String id) {
        return bookingService.getBookingById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found with id: " + id)))
                .flatMap(booking -> bookingService.deleteBooking(id));
    }
}
