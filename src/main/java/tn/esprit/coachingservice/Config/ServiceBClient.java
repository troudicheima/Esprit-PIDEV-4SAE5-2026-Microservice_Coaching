package tn.esprit.coachingservice.Config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import tn.esprit.coachingservice.Dto.UserDto;

@FeignClient(name ="user-management-service",url = "http://localhost:8082")
public interface ServiceBClient {

    @GetMapping("/api/users/email/{email}")
    UserDto getUserByEmail(@PathVariable("email") String email,
                           @RequestHeader("Authorization") String authorizationHeader);

    @GetMapping("/api/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id,
                        @RequestHeader("Authorization") String authorizationHeader);


}
