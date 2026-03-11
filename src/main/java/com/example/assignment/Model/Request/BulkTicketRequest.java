package com.example.assignment.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkTicketRequest {
    private Integer[] ticketId;
    private boolean paymentStatus;

}
