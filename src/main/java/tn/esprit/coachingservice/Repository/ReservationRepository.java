package tn.esprit.coachingservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coachingservice.Entity.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findBySeanceId(int seanceId);
}
