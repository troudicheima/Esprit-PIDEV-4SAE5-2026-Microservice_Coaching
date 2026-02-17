package tn.esprit.coachingservice.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coachingservice.Entity.Seance;
import tn.esprit.coachingservice.Repository.SeanceRepository;

import java.util.List;

@Service
public class SeanceService {


    @Autowired
    private SeanceRepository seanceRepository;

    public Seance createSeance(Seance seance) {
        return seanceRepository.save(seance);
    }

    public Seance getSeanceById(int id) {
        return seanceRepository.findById(id).orElse(null);
    }

    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }


    public Seance updateSeance(int id, Seance seanceDetails) {
        Seance seance = seanceRepository.findById(id).orElse(null);
        if (seance != null) {
            seance.setGoodName(seanceDetails.getGoodName());
            seance.setSeanceDate(seanceDetails.getSeanceDate());
            seance.setSeanceTime(seanceDetails.getSeanceTime());
            return seanceRepository.save(seance);
        }
        return null;
    }

    public void deleteSeance(int id) {
        seanceRepository.deleteById(id);
    }


}
