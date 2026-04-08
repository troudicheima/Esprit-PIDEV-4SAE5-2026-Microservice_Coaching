package tn.esprit.coachingservice.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coachingservice.Dto.UserDto;
import tn.esprit.coachingservice.Entity.Seance;
import tn.esprit.coachingservice.Service.SeanceService;

import java.util.List;

@RestController
@RequestMapping("/api/seances")
public class SeanceController {
    @Autowired
    private SeanceService seanceService;

    @Autowired
    private tn.esprit.coachingservice.Feign.UserServiceClient userServiceClient;

    @PostMapping
    public ResponseEntity<Seance> createSeance(@RequestBody Seance seance) {
        Seance created = seanceService.createSeance(seance);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seance> getSeanceById(@PathVariable int id) {
        Seance seance = seanceService.getSeanceById(id);
        return seance != null ? ResponseEntity.ok(seance) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Seance> getAllSeances() {
        return seanceService.getAllSeances();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Seance> updateSeance(@PathVariable int id, @RequestBody Seance seance) {
        Seance updated = seanceService.updateSeance(id, seance);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeance(@PathVariable int id) {
        seanceService.deleteSeance(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<Seance>> getSeancesByTutor(@PathVariable Long tutorId) {
        return ResponseEntity.ok(seanceService.getSeancesByTutor(tutorId));
    }



    @PostMapping("/by-tutor-email")
    public ResponseEntity<Seance> createSeanceForTutor(
            @RequestParam String tutorEmail,
            @RequestBody Seance seance,
            @RequestHeader("Authorization") String authorizationHeader) {

        // Récupérer le profil du tuteur depuis user-management-service
        UserDto tutor = userServiceClient.getUserByEmail(tutorEmail, authorizationHeader);
        if (tutor == null) {
            return ResponseEntity.badRequest().build();
        }

        seance.setTutorId(tutor.getId());  // ID de user_profiles
        Seance created = seanceService.createSeance(seance);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }





}
