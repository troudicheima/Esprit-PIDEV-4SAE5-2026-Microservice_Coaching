package tn.esprit.coachingservice.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String studidname;      // nom / identifiant de l'étudiant
    private LocalDate merenumber;   // date (ex: date de réservation)
    private String status;          // statut de la réservation

    @ManyToOne
    @JoinColumn(name = "seance_id")
    @JsonIgnore
    private Seance seance;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudidname() {
        return studidname;
    }

    public void setStudidname(String studidname) {
        this.studidname = studidname;
    }

    public LocalDate getMerenumber() {
        return merenumber;
    }

    public void setMerenumber(LocalDate merenumber) {
        this.merenumber = merenumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }
}
