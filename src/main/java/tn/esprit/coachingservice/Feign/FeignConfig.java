package tn.esprit.coachingservice.Feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configuration globale Feign - s'applique à tous les clients Feign
// L'intercepteur d'auth est géré via @Component dans FeignAuthInterceptor
@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

}
