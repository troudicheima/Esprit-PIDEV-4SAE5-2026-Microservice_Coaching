package tn.esprit.coachingservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coachingservice.Entity.Seance;

import java.util.List;

public interface SeanceRepository extends JpaRepository<Seance, Integer> {
    List<Seance> findByTutorId(Long tutorId);

}
