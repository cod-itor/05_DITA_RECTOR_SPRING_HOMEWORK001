package com.example.assignment.Controller;

import com.example.assignment.Model.Entities.ResponseBody;
import com.example.assignment.Model.Entities.Ticket;
import com.example.assignment.Model.Entities.TicketStatus;
import com.example.assignment.Model.Request.BulkTicketRequest;
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
   private final AtomicLong autoGenerateId = new AtomicLong();

    public TicketController(){
        ticketList.add(new Ticket(autoGenerateId.getAndIncrement() ,"Jmol", LocalDate.now(),"Seoul Station", "Phnom Penh",23.34,false, TicketStatus.BOOKED, 3));
        ticketList.add(new Ticket(autoGenerateId.getAndIncrement() ,"Jmol", LocalDate.now(),"Seoul Station", "Phnom Penh",23.34,true, TicketStatus.CANCELED, 3));
        ticketList.add(new Ticket(autoGenerateId.getAndIncrement() ,"Jmol", LocalDate.now(),"Seoul Station", "Phnom Penh",23.34,false, TicketStatus.COMPLETED, 3));
    }
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
    @PostMapping("/create")
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
    @GetMapping("/{ticket-id}")
    public ResponseEntity<Ticket> getTicketById (@PathVariable("ticket-id") long ticketId ){
        for(Ticket ticket : ticketList){
            if(ticket.getTicketId().equals(ticketId)){
                return ResponseEntity.ok(ticket);
            }
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<Ticket>> getTicketByName(@RequestParam String ticketName){
        for(Ticket ticket : ticketList){
            if(ticket.getPassengerName().toLowerCase().contains(ticketName.toLowerCase())){
                return ResponseEntity.ok(ticketList);
            }
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("{ticket-id}")
    public ResponseEntity<Ticket> updateTicketById(@PathVariable("ticket-id") long ticketId , @RequestBody TicketRequestDto ticketRequestDto){
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
                return ResponseEntity.ok(ticket);
            }
        }
        return ResponseEntity.badRequest().build();

    }
    @Operation(summary = "Delete a ticket by ID")
    @DeleteMapping("/delete/{ticket-id}")
    public ResponseEntity<String> deleteTicketById(@PathVariable("ticket-id") long ticketId){
        boolean remove = ticketList.removeIf(ticket -> ticket.getTicketId() == ticketId);
        if(!remove ){
            return ResponseEntity.ok("SuccessFully Deleted the Book");
        }else{
            return ResponseEntity.notFound().build();
        }
    }


@GetMapping("/filter")
    public ResponseEntity<Ticket> filterStatusAndDate(@RequestParam TicketStatus ticketStatus, @RequestParam LocalDate date ){
        for(Ticket ticket : ticketList){
            if(ticket.getTicketStatus().equals(ticketStatus) & ticket.getTravelDate().equals(date)){

                return ResponseEntity.ok(ticket);
            }
        }
        return ResponseEntity.notFound().build();
}
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
