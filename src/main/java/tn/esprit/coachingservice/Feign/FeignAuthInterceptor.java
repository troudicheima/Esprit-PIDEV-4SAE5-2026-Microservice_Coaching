package tn.esprit.coachingservice.Feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignAuthInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String token = obtainToken();
        if (token != null) {
            template.header("Authorization", "Bearer " + token);
        }
    }

    private String obtainToken() {
        return null;
    }

}
