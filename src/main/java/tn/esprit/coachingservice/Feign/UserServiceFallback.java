package tn.esprit.coachingservice.Feign;

import org.springframework.stereotype.Component;
import tn.esprit.coachingservice.Dto.UserDto;

@Component
public class UserServiceFallback implements UserServiceClient {

    @Override
    public UserDto getUserByEmail(String email, String authorizationHeader) {
        return null;
    }

    @Override
    public UserDto getUserById(Long id, String authorizationHeader) {
        return null;
    }

}
