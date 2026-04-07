# Configuration OpenFeign - User Management Service

Ce document explique comment configurer la communication depuis le microservice `user-management-service` vers `Coaching-service`.

## 1. Dépendances Maven

Dans le `pom.xml` du `user-management-service`:

```xml
<!-- OpenFeign -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>

<!-- Circuit Breaker (optionnel mais recommandé) -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
</dependency>

<!-- Eureka Client -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

## 2. Activation d'OpenFeign

Dans la classe principale du microservice:

```java
@SpringBootApplication
@EnableFeignClients
public class UserManagementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserManagementServiceApplication.class, args);
    }
}
```

## 3. Création du Client Feign

Créer l'interface client dans le package `Feign`:

```java
package tn.esprit.usermanagementservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import tn.esprit.usermanagementservice.Dto.CoachingDto;

@FeignClient(name = "coaching-service", url = "http://localhost:5057",
             fallback = CoachingServiceFallback.class)
public interface CoachingServiceClient {

    @GetMapping("/Coaching-service/api/reservations/seance/{seanceId}")
    List<Object> getReservationsBySeance(
            @PathVariable("seanceId") int seanceId,
            @RequestHeader("Authorization") String authorizationHeader);

    @GetMapping("/Coaching-service/api/seances/{id}")
    Object getSeanceById(
            @PathVariable("id") int id,
            @RequestHeader("Authorization") String authorizationHeader);
}
```

## 4. Fallback (Résilience)

```java
package tn.esprit.usermanagementservice.Feign;

import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
public class CoachingServiceFallback implements CoachingServiceClient {

    @Override
    public List<Object> getReservationsBySeance(int seanceId, String authorizationHeader) {
        return Collections.emptyList();
    }

    @Override
    public Object getSeanceById(int id, String authorizationHeader) {
        return null;
    }
}
```

## 5. Configuration (application.yml)

```yaml
spring:
  application:
    name: user-management-service

server:
  port: 8082

# Feign Configuration
feign:
  client:
    config:
      default:
        connect-timeout: 5000
        read-timeout: 10000
        logger-level: basic
      coaching-service:
        connect-timeout: 3000
        read-timeout: 5000

# Eureka
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true
```

## 6. Configuration Java (Optionnel)

```java
package tn.esprit.usermanagementservice.Feign;

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

    @Bean
    public RequestInterceptor feignAuthInterceptor() {
        return new FeignAuthInterceptor();
    }
}
```

## 7. Intercepteur d'Authentification (Optionnel)

```java
package tn.esprit.usermanagementservice.Feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignAuthInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // Ajouter le token JWT si nécessaire
        // String token = obtainToken();
        // template.header("Authorization", "Bearer " + token);
    }
}
```

## 8. Utilisation dans un Service

```java
package tn.esprit.usermanagementservice.Service;

import tn.esprit.usermanagementservice.Feign.CoachingServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCoachingService {

    private final CoachingServiceClient coachingServiceClient;

    public Object getSeanceDetails(int seanceId, String token) {
        return coachingServiceClient.getSeanceById(seanceId, "Bearer " + token);
    }
}
```

## Points Importants

| Élément | Valeur Coaching-service |
|---------|------------------------|
| URL Base | `http://localhost:5057` |
| Context Path | `/Coaching-service` |
| Port | `5057` |
| Nom Eureka | `COACHING-SERVICE` |

## Résumé des Étapes

1. Ajouter les dépendances Maven
2. Ajouter `@EnableFeignClients` sur la classe principale
3. Créer l'interface client Feign avec les endpoints souhaités
4. (Optionnel) Créer la classe Fallback
5. Configurer `application.yml`
6. Injecter le client dans les services