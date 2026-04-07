package tn.esprit.coachingservice.Feign;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    //@Bean
   // public RequestInterceptor feignAuthInterceptor() {
        //return new FeignAuthInterceptor();
    //}

}
