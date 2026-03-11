package com.example.assignment.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkTicketPostRequest {
    private Integer[] ticketId;
    private boolean paymentStatus;


}