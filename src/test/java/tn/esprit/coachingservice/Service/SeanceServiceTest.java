package tn.esprit.coachingservice.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.coachingservice.Entity.Seance;
import tn.esprit.coachingservice.Repository.SeanceRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SeanceServiceTest {

    @Mock
    private SeanceRepository seanceRepository;

    @InjectMocks
    private SeanceService seanceService;

    private Seance seance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seance = new Seance();
        seance.setId(1);
        seance.setGoodName("Yoga");
        seance.setSeanceDate(LocalDate.now());
        seance.setSeanceTime(LocalTime.now());
        seance.setTutorId(10L);
    }

    @Test
    void createSeance() {
        when(seanceRepository.save(any(Seance.class))).thenReturn(seance);

        Seance created = seanceService.createSeance(seance);

        assertNotNull(created);
        assertEquals(1, created.getId());
        assertEquals("Yoga", created.getGoodName());
        verify(seanceRepository, times(1)).save(seance);
    }

    @Test
    void getSeanceById() {
        when(seanceRepository.findById(1)).thenReturn(Optional.of(seance));

        Seance found = seanceService.getSeanceById(1);

        assertNotNull(found);
        assertEquals("Yoga", found.getGoodName());
        verify(seanceRepository, times(1)).findById(1);
    }

    @Test
    void getSeanceById_NotFound() {
        when(seanceRepository.findById(1)).thenReturn(Optional.empty());

        Seance found = seanceService.getSeanceById(1);

        assertNull(found);
    }

    @Test
    void getAllSeances() {
        when(seanceRepository.findAll()).thenReturn(Arrays.asList(seance, new Seance()));

        List<Seance> seances = seanceService.getAllSeances();

        assertNotNull(seances);
        assertEquals(2, seances.size());
        verify(seanceRepository, times(1)).findAll();
    }

    @Test
    void updateSeance() {
        Seance updatedDetails = new Seance();
        updatedDetails.setGoodName("Pilates");

        when(seanceRepository.findById(1)).thenReturn(Optional.of(seance));
        when(seanceRepository.save(any(Seance.class))).thenReturn(seance);

        Seance result = seanceService.updateSeance(1, updatedDetails);

        assertNotNull(result);
        assertEquals("Pilates", result.getGoodName());
        verify(seanceRepository, times(1)).findById(1);
        verify(seanceRepository, times(1)).save(seance);
    }

    @Test
    void updateSeance_NotFound() {
        when(seanceRepository.findById(1)).thenReturn(Optional.empty());

        Seance result = seanceService.updateSeance(1, new Seance());

        assertNull(result);
        verify(seanceRepository, never()).save(any(Seance.class));
    }

    @Test
    void deleteSeance() {
        doNothing().when(seanceRepository).deleteById(1);

        seanceService.deleteSeance(1);

        verify(seanceRepository, times(1)).deleteById(1);
    }

    @Test
    void getSeancesByTutor() {
        when(seanceRepository.findByTutorId(10L)).thenReturn(Arrays.asList(seance));

        List<Seance> seances = seanceService.getSeancesByTutor(10L);

        assertNotNull(seances);
        assertEquals(1, seances.size());
        verify(seanceRepository, times(1)).findByTutorId(10L);
    }
}
