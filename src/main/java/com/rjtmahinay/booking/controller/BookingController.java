package com.rjtmahinay.booking.controller;

import com.rjtmahinay.booking.model.json.BookingRequest;
import com.rjtmahinay.booking.model.json.BookingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/booking")
@Tag(name = "Booking Controller", description = "APIs for flight booking operations")
public class BookingController {

    @PostMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Book a flight", description = "Creates a new flight booking with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking successful", 
                    content = @Content(schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", 
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", 
                    content = @Content)
    })
    public Mono<ResponseEntity<BookingResponse>> bookFlight(@RequestBody Mono<BookingRequest> requestMono) {

        return requestMono
                .doOnNext(request -> log.info("Received booking request for flight: {}", request.getFlightNumber()))
                .flatMap(request -> {
                    // Simulate a successful booking
                    BookingResponse response = new BookingResponse();
                    response.setStatus("Success");
                    response.setConfirmationNumber(generateConfirmationNumber());
                    response.setETicket("E" + generateConfirmationNumber());
                    response.setMessage("Booking for flight " + request.getFlightNumber() + " is complete.");

                    return Mono.just(ResponseEntity.ok(response));
                })
                .onErrorResume(e -> {

                    log.error("Booking failed due to an error.", e);
                    BookingResponse response = new BookingResponse();
                    response.setStatus("Failure");
                    response.setMessage("Booking failed due to an internal error: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
                });
    }

    private String generateConfirmationNumber() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
