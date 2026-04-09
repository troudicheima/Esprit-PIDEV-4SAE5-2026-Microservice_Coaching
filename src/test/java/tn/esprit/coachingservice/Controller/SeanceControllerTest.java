package tn.esprit.coachingservice.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tn.esprit.coachingservice.Dto.UserDto;
import tn.esprit.coachingservice.Entity.Seance;
import tn.esprit.coachingservice.Feign.UserServiceClient;
import tn.esprit.coachingservice.Service.SeanceService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SeanceControllerTest {

    @Mock
    private SeanceService seanceService;

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private SeanceController seanceController;

    private Seance seance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seance = new Seance();
        seance.setId(1);
        seance.setGoodName("Yoga");
    }

    @Test
    void createSeance() {
        when(seanceService.createSeance(any(Seance.class))).thenReturn(seance);

        ResponseEntity<Seance> response = seanceController.createSeance(seance);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(seance, response.getBody());
    }

    @Test
    void getSeanceById() {
        when(seanceService.getSeanceById(1)).thenReturn(seance);

        ResponseEntity<Seance> response = seanceController.getSeanceById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(seance, response.getBody());
    }

    @Test
    void getSeanceById_NotFound() {
        when(seanceService.getSeanceById(1)).thenReturn(null);

        ResponseEntity<Seance> response = seanceController.getSeanceById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllSeances() {
        when(seanceService.getAllSeances()).thenReturn(Arrays.asList(seance));

        List<Seance> seances = seanceController.getAllSeances();

        assertEquals(1, seances.size());
    }

    @Test
    void updateSeance() {
        when(seanceService.updateSeance(eq(1), any(Seance.class))).thenReturn(seance);

        ResponseEntity<Seance> response = seanceController.updateSeance(1, seance);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(seance, response.getBody());
    }

    @Test
    void deleteSeance() {
        doNothing().when(seanceService).deleteSeance(1);

        ResponseEntity<Void> response = seanceController.deleteSeance(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(seanceService, times(1)).deleteSeance(1);
    }

    @Test
    void getSeancesByTutor() {
        when(seanceService.getSeancesByTutor(10L)).thenReturn(Arrays.asList(seance));

        ResponseEntity<List<Seance>> response = seanceController.getSeancesByTutor(10L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createSeanceForTutor() {
        UserDto tutor = new UserDto();
        tutor.setId(10L);
        when(userServiceClient.getUserByEmail(anyString(), anyString())).thenReturn(tutor);
        when(seanceService.createSeance(any(Seance.class))).thenReturn(seance);

        ResponseEntity<Seance> response = seanceController.createSeanceForTutor("test@test.com", seance, "Bearer token");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(seance, response.getBody());
        verify(userServiceClient, times(1)).getUserByEmail("test@test.com", "Bearer token");
        assertEquals(10L, seance.getTutorId());
    }

    @Test
    void createSeanceForTutor_UserNotFound() {
        when(userServiceClient.getUserByEmail(anyString(), anyString())).thenReturn(null);

        ResponseEntity<Seance> response = seanceController.createSeanceForTutor("test@test.com", seance, "Bearer token");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(seanceService, never()).createSeance(any(Seance.class));
    }
}
