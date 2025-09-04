CREATE TABLE IF NOT EXISTS bookings (
    id VARCHAR(36) PRIMARY KEY,
    flight_number VARCHAR(20) NOT NULL,
    origin VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    passenger_name VARCHAR(200) NOT NULL,
    status VARCHAR(50) NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    booking_date TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    version BIGINT
);
