package tn.esprit.coachingservice.Dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void testGettersAndSetters() {
        UserDto userDto = new UserDto();
        
        userDto.setId(1L);
        userDto.setEmail("test@test.com");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setRole("COACH");

        assertEquals(1L, userDto.getId());
        assertEquals("test@test.com", userDto.getEmail());
        assertEquals("John", userDto.getFirstName());
        assertEquals("Doe", userDto.getLastName());
        assertEquals("COACH", userDto.getRole());
    }
}
