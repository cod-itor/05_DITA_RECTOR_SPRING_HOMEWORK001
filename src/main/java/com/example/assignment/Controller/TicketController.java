package com.example.assignment.Controller;

import com.example.assignment.Model.Entities.ResponseBody;
import com.example.assignment.Model.Entities.Ticket;
import com.example.assignment.Model.Entities.TicketStatus;
import com.example.assignment.Model.Request.BulkTicketRequest;
import com.example.assignment.Model.Request.DeleteBodyResponse;
import com.example.assignment.Model.Request.TicketRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
   private final ArrayList<Ticket> ticketList = new ArrayList<>();
   private final AtomicLong autoGenerateId = new AtomicLong(1);

    public TicketController(){
        ticketList.add(new Ticket(autoGenerateId.getAndIncrement() ,"Jmol", LocalDate.now(),"Seoul Station", "Phnom Penh",23.34,false, TicketStatus.BOOKED, 3));
        ticketList.add(new Ticket(autoGenerateId.getAndIncrement() ,"Jmol", LocalDate.now(),"Seoul Station", "Phnom Penh",23.34,true, TicketStatus.CANCELED, 3));
        ticketList.add(new Ticket(autoGenerateId.getAndIncrement() ,"Jmol", LocalDate.now(),"Seoul Station", "Phnom Penh",23.34,false, TicketStatus.COMPLETED, 3));
    }
    @Operation(summary = "Get all tickets") //finished
    @GetMapping
    public ResponseEntity<ResponseBody<ArrayList<Ticket>>> getAllTicket(){
       ResponseBody<ArrayList<Ticket>> response = new ResponseBody<>(
               true,
               "Tickets retrieved successfully",
               "200 OK",
               ticketList,
               Instant.now()

       );
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Create a new Ticket")
    @PostMapping
    public ResponseEntity<ResponseBody<Ticket>> saveTicket(@RequestBody TicketRequestDto ticketRequestDto){
        Ticket newTicket = new Ticket(
                autoGenerateId.getAndIncrement(),
                ticketRequestDto.getPassengerName(),
                ticketRequestDto.getTravelDate(),
                ticketRequestDto.getSourceStation(),
                ticketRequestDto.getDestinationStation(),
                ticketRequestDto.getPrice(),
                ticketRequestDto.getPaymentStatus(),
                ticketRequestDto.getTicketStatus(),
                ticketRequestDto.getSeatNumber()
        );
        ticketList.add(newTicket);
        ResponseBody<Ticket> responseBody = new ResponseBody<>(
               true,
               "Tickets retrieved successfully",
               "200 OK",
               newTicket,
                Instant.now()
       );
        return ResponseEntity.status(201).body(responseBody);


    }
    @Operation(summary = "Get a ticket by ID")
    @GetMapping("/{ticket-id}")
    public ResponseEntity<ResponseBody<ArrayList<Ticket>>> getTicketById (@PathVariable("ticket-id") long ticketId ){
        ArrayList<Ticket> ticketIdSearch = new ArrayList<>();
        for(Ticket ticket : ticketList){
            if(ticket.getTicketId().equals(ticketId)){
                ticketIdSearch.add(ticket);
            }
            ResponseBody<ArrayList<Ticket>> response = new ResponseBody<>(
                    true,
                    "Tickets retrieved successfully",
                    "200 OK",
                    ticketIdSearch,
                    Instant.now()
            );
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }
    @Operation(summary = "Search for ticket(s) by passenger name") //finished
    @GetMapping("/search")
    public ResponseEntity<ResponseBody<ArrayList<Ticket>>> getTicketByName(@RequestParam String ticketName){
        ArrayList<Ticket> searchTicketList = new ArrayList<>();
        for(Ticket ticket : ticketList){
            if(ticket.getPassengerName().equalsIgnoreCase(ticketName)){
              searchTicketList.add(ticket);
            }
        }
        if(searchTicketList.isEmpty()){
            ResponseBody<ArrayList<Ticket>> response = new ResponseBody<>(
                    true,
                    "No tickets found for the given passenger name",
                    "200 OK",
                    searchTicketList,
                    Instant.now()

            );
            return ResponseEntity.ok(response);

        }
        ResponseBody<ArrayList<Ticket>> response = new ResponseBody<>(
                true,
                "Tickets fetched successfully",
                "200 OK",
                searchTicketList,
                Instant.now()
        );

        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Update a ticket by ID")
    @PutMapping("{ticket-id}") //not done yet
    public ResponseEntity<ResponseBody<ArrayList<Ticket>>> updateTicketById(@PathVariable("ticket-id") long ticketId , @RequestBody TicketRequestDto ticketRequestDto){
        ArrayList<Ticket> updateTicketList = new ArrayList<>();

        for(Ticket ticket : ticketList){
            if(ticket.getTicketId().equals(ticketId)){
                ticket.setPassengerName(ticketRequestDto.getPassengerName());
                ticket.setTravelDate(ticketRequestDto.getTravelDate());
                ticket.setSourceStation(ticketRequestDto.getSourceStation());
                ticket.setDestinationStation(ticketRequestDto.getDestinationStation());
                ticket.setPrice(ticketRequestDto.getPrice());
                ticket.setPaymentStatus(ticketRequestDto.getPaymentStatus());
                ticket.setTicketStatus(ticketRequestDto.getTicketStatus());
                ticket.setSeatNumber(ticketRequestDto.getSeatNumber());
            }
            updateTicketList.add(ticket);
        }
        if(updateTicketList.isEmpty()){
            ResponseBody<ArrayList<Ticket>> response = new ResponseBody<>(
                    false,
                    "No tickets found with the given ID.",
                    "404 NOT_FOUND",
                    null,
                    Instant.now()

            );
            return ResponseEntity.status(404).body(response);
        }
        ResponseBody<ArrayList<Ticket>> response = new ResponseBody<>(
                true,
                "Ticket updated successfully",
                "200 OK",
                updateTicketList,
                Instant.now()
        );

        return ResponseEntity.ok(response);

    }
//    {
//        "success": true,
//            "message": "Ticket updated successfully",
//            "status": "200 OK",
//            "payload": {
//        "ticketId": 70,
//                "passengerName": "Jmol",
//                "travelDate": "2026-03-11",
//                "sourceStation": "string",
//                "destinationStation": "string",
//                "price": 0,
//                "paymentStatus": true,
//                "ticketStatus": "BOOKED",
//                "seatNumber": "string"
//    },
//        "timestamp": "2026-03-11T12:03:28.298429277Z"
//    }
//    {
//        "success": false,
//            "message": "No tickets found with the given ID.",
//            "status": "404 NOT_FOUND",
//            "timestamp": "2026-03-11T12:01:23.530856224Z"
//    }
    @Operation(summary = "Delete a ticket by ID")//finished
    @DeleteMapping("/delete/{ticket-id}")
    public ResponseEntity<DeleteBodyResponse<ArrayList<Ticket>>> deleteTicketById(@PathVariable("ticket-id") long ticketId){
        boolean remove = ticketList.removeIf(ticket -> ticket.getTicketId() == ticketId);
        if(!remove ){
            DeleteBodyResponse<ArrayList<Ticket>> response = new DeleteBodyResponse<>(
                    false,
                    "Ticket not found",
                    "404 NOT_FOUND",
                    Instant.now()
            );
            return ResponseEntity.status(404).body(response);
        };

            DeleteBodyResponse<ArrayList<Ticket>> response = new DeleteBodyResponse<>(
                    true,
                    "Ticket deleted successfully",
                    "200 OK",
                    Instant.now()
            );
            return ResponseEntity.ok(response);


    }

@Operation(summary = "Filter tickets by status and travel date")
@GetMapping("/filter") //finished
    public ResponseEntity<ResponseBody<ArrayList<Ticket>>> filterStatusAndDate(@RequestParam TicketStatus ticketStatus, @RequestParam LocalDate date ){
        ArrayList<Ticket> filterList = new ArrayList<>();
        for(Ticket ticket : ticketList){
            if(ticket.getTicketStatus().equals(ticketStatus) & ticket.getTravelDate().equals(date)){
                filterList.add(ticket);
            }
        }
        if(filterList.isEmpty()){
            ResponseBody<ArrayList<Ticket>> response = new ResponseBody<>(
                    true,
                    "No tickets found with given filters",
                    "200 OK",
                    filterList,
                    Instant.now()

            );
            return ResponseEntity.ok(response);

        }
    ResponseBody<ArrayList<Ticket>> response = new ResponseBody<>(
            true,
            "Tickets filtered successfully",
            "200 OK",
            filterList,
            Instant.now()
    );
        return ResponseEntity.ok(response);
}
@Operation(summary = "Create multiple new tickets")
@PostMapping("/bulk")
    public ResponseEntity<ResponseBody<ArrayList<Ticket>>> createMultipleTickets(@RequestBody List<TicketRequestDto> requestDtoList ) {
    ArrayList<Ticket> newCreatedTicket = new ArrayList<>();
    for (TicketRequestDto ticketRequestDto : requestDtoList) {
        Ticket newticket = new Ticket(
                autoGenerateId.getAndIncrement(),
                ticketRequestDto.getPassengerName(),
                ticketRequestDto.getTravelDate(),
                ticketRequestDto.getSourceStation(),
                ticketRequestDto.getDestinationStation(),
                ticketRequestDto.getPrice(),
                ticketRequestDto.getPaymentStatus(),
                ticketRequestDto.getTicketStatus(),
                ticketRequestDto.getSeatNumber()
        );
        newCreatedTicket.add(newticket);
        ticketList.add(newticket);
    }
    ResponseBody<ArrayList<Ticket>> response = new ResponseBody<>(
            true,
            "Tickets retrieved successfully",
            "200 OK",
            newCreatedTicket,
            Instant.now()
    );
    return ResponseEntity.ok(response);

}







}
