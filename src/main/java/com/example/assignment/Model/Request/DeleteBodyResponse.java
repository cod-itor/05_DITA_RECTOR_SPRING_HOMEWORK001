package com.example.assignment.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBodyResponse<T> {
    private boolean success;
    private String message;
    private String status;
    private Instant timestamp;
}
