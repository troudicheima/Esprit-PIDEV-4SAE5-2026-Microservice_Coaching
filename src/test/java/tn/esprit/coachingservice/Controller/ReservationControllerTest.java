package tn.esprit.coachingservice.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tn.esprit.coachingservice.Entity.Reservation;
import tn.esprit.coachingservice.Service.ReservationService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = new Reservation();
        reservation.setId(1);
        reservation.setStatus("CONFIRMED");
    }

    @Test
    void createReservation() {
        when(reservationService.createReservation(eq(1), any(Reservation.class))).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationController.createReservation(1, reservation);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(reservation, response.getBody());
    }

    @Test
    void createReservation_NotFound() {
        when(reservationService.createReservation(eq(1), any(Reservation.class))).thenReturn(null);

        ResponseEntity<Reservation> response = reservationController.createReservation(1, reservation);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getReservationById() {
        when(reservationService.getReservationById(1)).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationController.getReservationById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservation, response.getBody());
    }

    @Test
    void getAllReservations() {
        when(reservationService.getAllReservations()).thenReturn(Arrays.asList(reservation));

        List<Reservation> reservations = reservationController.getAllReservations();

        assertEquals(1, reservations.size());
    }

    @Test
    void getReservationsBySeance() {
        when(reservationService.getReservationsBySeance(1)).thenReturn(Arrays.asList(reservation));

        List<Reservation> reservations = reservationController.getReservationsBySeance(1);

        assertEquals(1, reservations.size());
    }

    @Test
    void updateReservation() {
        when(reservationService.updateReservation(eq(1), any(Reservation.class))).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationController.updateReservation(1, reservation);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservation, response.getBody());
    }

    @Test
    void deleteReservation() {
        doNothing().when(reservationService).deleteReservation(1);

        ResponseEntity<Void> response = reservationController.deleteReservation(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reservationService, times(1)).deleteReservation(1);
    }
}
