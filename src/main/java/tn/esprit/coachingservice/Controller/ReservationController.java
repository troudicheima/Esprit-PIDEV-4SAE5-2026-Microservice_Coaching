package tn.esprit.coachingservice.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coachingservice.Entity.Reservation;
import tn.esprit.coachingservice.Service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/seances/{seanceId}/reservations")
    public ResponseEntity<Reservation> createReservation(@PathVariable int seanceId, @RequestBody Reservation reservation) {
        Reservation created = reservationService.createReservation(seanceId, reservation);
        return created != null ? new ResponseEntity<>(created, HttpStatus.CREATED)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable int id) {
        Reservation reservation = reservationService.getReservationById(id);
        return reservation != null ? ResponseEntity.ok(reservation) : ResponseEntity.notFound().build();
    }


    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/seances/{seanceId}/reservations")
    public List<Reservation> getReservationsBySeance(@PathVariable int seanceId) {
        return reservationService.getReservationsBySeance(seanceId);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable int id, @RequestBody Reservation reservation) {
        Reservation updated = reservationService.updateReservation(id, reservation);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }



    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }


}
