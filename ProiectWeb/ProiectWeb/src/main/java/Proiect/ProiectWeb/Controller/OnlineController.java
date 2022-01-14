package Proiect.ProiectWeb.Controller;

import Proiect.ProiectWeb.Service.OnlineService;
import Proiect.ProiectWeb.Tables.Guests;
import Proiect.ProiectWeb.Tables.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OnlineController {

    @Autowired
    private OnlineService service;

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> getReservations(@RequestParam long phone){
        List<Reservation> reservations = service.getGuestReservations(phone);
        return reservations.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reservations);
    }

    @PostMapping("reservation")
    public ResponseEntity<Void> makeReservation(@RequestParam ("hotelName") String hotelName,
                                                @RequestParam long phone,
                                                @RequestParam ("checkIn") String checkIn,
                                                @RequestParam ("checkOut") String checkOut,
                                                @RequestParam long roomId){
        service.makeReservation(hotelName, phone, checkIn, checkOut, roomId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/guest")
    public ResponseEntity<Void> saveGuest(@RequestBody Guests guest){
        service.saveGuest(guest);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/guest")
    public ResponseEntity<Void> deleteGuest(@RequestParam long phone){
        service.removeGuest(phone);
        return ResponseEntity.accepted().build();
    }
}
