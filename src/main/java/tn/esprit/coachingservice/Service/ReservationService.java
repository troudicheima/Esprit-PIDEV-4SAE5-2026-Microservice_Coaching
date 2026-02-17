package tn.esprit.coachingservice.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coachingservice.Entity.Reservation;
import tn.esprit.coachingservice.Entity.Seance;
import tn.esprit.coachingservice.Repository.ReservationRepository;
import tn.esprit.coachingservice.Repository.SeanceRepository;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    public Reservation createReservation(int seanceId, Reservation reservation) {
        Seance seance = seanceRepository.findById(seanceId).orElse(null);
        if (seance != null) {
            reservation.setSeance(seance);
            return reservationRepository.save(reservation);
        }
        return null;
    }


    public Reservation getReservationById(int id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsBySeance(int seanceId) {
        return reservationRepository.findBySeanceId(seanceId);
    }



    public Reservation updateReservation(int id, Reservation reservationDetails) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation != null) {
            reservation.setStudidname(reservationDetails.getStudidname());
            reservation.setMerenumber(reservationDetails.getMerenumber());
            reservation.setStatus(reservationDetails.getStatus());
            // Changer de séance si nécessaire
            if (reservationDetails.getSeance() != null) {
                reservation.setSeance(reservationDetails.getSeance());
            }
            return reservationRepository.save(reservation);
        }
        return null;
    }

    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }


}
