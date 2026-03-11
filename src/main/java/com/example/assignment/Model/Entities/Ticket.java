package com.example.assignment.Model.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private Long ticketId;
    private String passengerName;
    private LocalDate travelDate;
    private String sourceStation;
    private String destinationStation;
    private double price;
    private Boolean paymentStatus;
    private TicketStatus ticketStatus;
    private String seatNumber;
}
