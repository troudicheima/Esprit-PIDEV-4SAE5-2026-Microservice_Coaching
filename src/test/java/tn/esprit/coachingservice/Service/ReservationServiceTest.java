package tn.esprit.coachingservice.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.coachingservice.Entity.Reservation;
import tn.esprit.coachingservice.Entity.Seance;
import tn.esprit.coachingservice.Repository.ReservationRepository;
import tn.esprit.coachingservice.Repository.SeanceRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private SeanceRepository seanceRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;
    private Seance seance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        seance = new Seance();
        seance.setId(1);
        seance.setGoodName("Yoga");
        
        reservation = new Reservation();
        reservation.setId(1);
        reservation.setStudidname("John Doe");
        reservation.setStatus("PENDING");
        reservation.setSeance(seance);
    }

    @Test
    void createReservation() {
        when(seanceRepository.findById(1)).thenReturn(Optional.of(seance));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation created = reservationService.createReservation(1, reservation);

        assertNotNull(created);
        assertEquals(seance, created.getSeance());
        verify(seanceRepository, times(1)).findById(1);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void createReservation_SeanceNotFound() {
        when(seanceRepository.findById(1)).thenReturn(Optional.empty());

        Reservation created = reservationService.createReservation(1, reservation);

        assertNull(created);
        verify(seanceRepository, times(1)).findById(1);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void getReservationById() {
        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

        Reservation found = reservationService.getReservationById(1);

        assertNotNull(found);
        assertEquals("John Doe", found.getStudidname());
    }

    @Test
    void getAllReservations() {
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));

        List<Reservation> reservations = reservationService.getAllReservations();

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
    }

    @Test
    void getReservationsBySeance() {
        when(reservationRepository.findBySeanceId(1)).thenReturn(Arrays.asList(reservation));

        List<Reservation> reservations = reservationService.getReservationsBySeance(1);

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
    }

    @Test
    void updateReservation() {
        Reservation details = new Reservation();
        details.setStudidname("Jane Doe");
        details.setStatus("CONFIRMED");

        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation updated = reservationService.updateReservation(1, details);

        assertNotNull(updated);
        assertEquals("Jane Doe", updated.getStudidname());
        assertEquals("CONFIRMED", updated.getStatus());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void updateReservation_NotFound() {
        when(reservationRepository.findById(1)).thenReturn(Optional.empty());

        Reservation updated = reservationService.updateReservation(1, new Reservation());

        assertNull(updated);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void deleteReservation() {
        doNothing().when(reservationRepository).deleteById(1);

        reservationService.deleteReservation(1);

        verify(reservationRepository, times(1)).deleteById(1);
    }
}
