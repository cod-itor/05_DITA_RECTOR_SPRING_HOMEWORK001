package com.example.assignment.Model.Request;

import com.example.assignment.Model.Entities.TicketStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class TicketRequestDto {
    private String passengerName;
    private LocalDate travelDate;
    private String sourceStation;
    private String destinationStation;
    private double price;
    private Boolean paymentStatus;
    private TicketStatus ticketStatus;
    private Integer seatNumber;
}
