package com.rjtmahinay.booking.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Payment information for the booking")
public class PaymentInfo {
    @Schema(description = "Credit/Debit card number", example = "4111111111111111", required = true)
    private String cardNumber;
    
    @Schema(description = "Card expiration date in MM/YY format", example = "12/25", required = true)
    private String expirationDate;
    
    @Schema(description = "Card security code", example = "123", minLength = 3, maxLength = 4, required = true)
    private String cvv;
}
